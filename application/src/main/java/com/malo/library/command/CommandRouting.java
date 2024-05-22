package com.malo.library.command;


import org.springframework.transaction.annotation.Isolation;

import java.util.List;

public interface CommandRouting<C extends Command> {
    List<CommandHandler<C>> getHandlers(Command cmd);

    boolean isParallelHandling(Command cmd);

    Isolation getIsolation(Command cmd);
}
