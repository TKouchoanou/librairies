package com.malo.library.command;


import com.malo.library.exception.business.BusinessException;
import com.malo.library.exception.business.BusinessExceptionWrapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class CommandManagerImpl implements CommandManager {
    private static final String ROLLBACK_LOG_FORMAT = "Rollback command {} execution failed for reason: {}";
    private static final String RECEIVED_COMMAND_LOG_FORMAT = "Command {} is received for treatment";


    CommandValidator commandValidator;

    CommandRouting commandRouting;

    PlatformTransactionManager platformTransactionManager;

    Logger logger;

    public CommandManagerImpl(CommandValidator commandValidator, CommandRouting commandRouting, PlatformTransactionManager platformTransactionManager, Logger logger) {
        this.commandValidator = commandValidator;
        this.commandRouting = commandRouting;
        this.platformTransactionManager = platformTransactionManager;
        this.logger = logger;
    }


    @Override
    public <T extends Command> T process(T command) throws BusinessException {

        logger.info(RECEIVED_COMMAND_LOG_FORMAT, command.getClass().getName());

        commandValidator.validBeforeHandling(command);

        TransactionStatus transactionStatus = getTransactionStatus(command);
        List<CommandHandler> commandHandlerList = getCommandHandlerList(command);
        CommandHandler.HandlingContext context = new CommandHandler.HandlingContext();
        boolean isSequential = isSequentialHandling(command);

        try {
            if (isSequential) {
                handleSequentially(commandHandlerList, command, context);
            } else {
                handleParallel(commandHandlerList, command, context);
            }
            commandValidator.validAfterHandling(command);
            platformTransactionManager.commit(transactionStatus);
            context.getOnSuccessActions().forEach(Runnable::run);
        } catch (Exception exception) {
            context.getOnFailureActions().forEach(Runnable::run);
            if (!transactionStatus.isCompleted() && !transactionStatus.isRollbackOnly()) {
                logger.warn(ROLLBACK_LOG_FORMAT, command.getClass().getName(), exception.getMessage());
                platformTransactionManager.rollback(transactionStatus);
            }

            if(exception instanceof BusinessExceptionWrapper exceptionWrapped){
                exceptionWrapped.throwWrappedCheckedException();
            }
            exception.printStackTrace();

            throw exception;
        }
        return command;
    }

    public void handleParallel(List<CommandHandler> commandHandlerList, Command command, CommandHandler.HandlingContext context) {
        CompletableFuture<?>[] workers = commandHandlerList
                .stream()
                .map(handler -> CompletableFuture.runAsync(() -> {
                    try {
                        handler.handle(command, context);
                    } catch (BusinessException e) {
                        throw new BusinessExceptionWrapper(e);
                    }
                }))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(workers).join();

    }

    public void handleSequentially(List<CommandHandler> commandHandlerList, Command command, CommandHandler.HandlingContext context) throws BusinessException {
        for (CommandHandler commandHandler : commandHandlerList) {
            commandHandler.handle(command, context);
        }
    }

    TransactionStatus getTransactionStatus(Command command) {
        var transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(commandRouting.getIsolation(command).value());
        return platformTransactionManager.getTransaction(transactionDefinition);
    }

    List<CommandHandler> getCommandHandlerList(Command command) {
        return commandRouting.getHandlers(command);
    }

    boolean isSequentialHandling(Command command) {
        return !commandRouting.isParallelHandling(command);
    }
}
