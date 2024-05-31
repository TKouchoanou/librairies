package com.malo.library.borrow.borrows;

import com.malo.library.command.CommandManager;
import com.malo.library.command.command.borrow.BorrowCommand;
import com.malo.library.exception.business.BusinessException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowController implements BorrowControllerApi{
    CommandManager commandManager;

    public BorrowController(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public Long create(BorrowCommand borrowCommand) throws BusinessException {
         this.commandManager.process(borrowCommand);
         return borrowCommand.getId();
    }
}
