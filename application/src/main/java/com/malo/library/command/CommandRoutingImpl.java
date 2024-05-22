package com.malo.library.command;


import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unused","rawtypes"})
@Component
public class CommandRoutingImpl implements CommandRouting {
    ApplicationContext context;

    public CommandRoutingImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public List<CommandHandler> getHandlers(Command cmd) {
        Command.UseCase usecase = getCommandUseCaseAnnotation(cmd);
        return Stream.of(usecase.handlers()).map(context::getBean).collect(Collectors.toList());
    }

    @Override
    public boolean isParallelHandling(Command cmd) {
        return getCommandUseCaseAnnotation(cmd).parallelHandling();
    }

    @Override
    public Isolation getIsolation(Command cmd) {
        return getCommandUseCaseAnnotation(cmd).isolation();
    }

    private Command.UseCase getCommandUseCaseAnnotation(Command cmd) {
        Command.UseCase useCaseAnnotation = cmd.getClass().getAnnotation(Command.UseCase.class);
        if (Objects.isNull(useCaseAnnotation)) {
            throw new RuntimeException(" command " + cmd.getClass() + " is not use case annotation");
        }
        return useCaseAnnotation;
    }
}
