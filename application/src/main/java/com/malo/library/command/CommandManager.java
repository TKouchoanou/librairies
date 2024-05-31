package com.malo.library.command;


import com.malo.library.exception.business.BusinessException;

public interface CommandManager {
    <T extends Command> T process(T command) throws BusinessException;

}
