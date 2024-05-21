package com.malo.library.command;


import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@Component
public class CommandRoutingImpl implements CommandRouting{
    ApplicationContext context;
    public CommandRoutingImpl(ApplicationContext context){
        this.context=context;
    }

    @Override
    public List<CommandHandler> getHandlers(Command cmd) {
        Command.Usecase usecase= getCommandUseCaseAnnotation(cmd);
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

   private Command.Usecase getCommandUseCaseAnnotation(Command cmd){
        Command.Usecase useCaseAnnotation = cmd.getClass().getAnnotation(Command.Usecase.class);
        if(Objects.isNull(useCaseAnnotation)){
          throw new RuntimeException(" command "+cmd.getClass()+" is not use case annotation");
        }
        return useCaseAnnotation;
    }
}
