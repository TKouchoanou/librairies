package command.handler;

import command.command.Command;

public interface CommandHandler <C extends Command> {

    void handle(C command);
}
