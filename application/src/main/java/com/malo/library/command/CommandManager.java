package com.malo.library.command;


public interface CommandManager {
    <T extends Command> T process(T command);

}
