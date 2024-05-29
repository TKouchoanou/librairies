package com.malo.library.borrow.returns;

import com.malo.library.command.CommandManager;
import com.malo.library.command.command.borrow.ReturnCommand;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnsController implements ReturnsControllerApi{
    CommandManager commandManager;

    public ReturnsController(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void handle(ReturnCommand returnCommand) {
        this.commandManager.process(returnCommand);
    }
}
