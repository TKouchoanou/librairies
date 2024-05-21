package com.malo.library.command;


public interface CommandValidator {
    void  validBeforeHandling(Command command);
    void  validAfterHandling(Command command);
}
