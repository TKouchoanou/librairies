package com.malo.library.borrow;

import com.malo.library.command.CommandManager;
import com.malo.library.command.command.borrow.BorrowCommand;
import com.malo.library.command.CommandHandler;
import com.malo.library.command.command.borrow.ReturnCommand;
import com.malo.library.command.handler.borrow.BorrowCommandHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BorrowController {

    CommandManager commandManager;

    public BorrowController(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @GetMapping("/borrow")
    void borrow() {
        var cmd = new BorrowCommand();
        cmd.setBookId(1L);
        cmd.setMemberId(1L);
        cmd.setReturnedDate(LocalDate.now().plusDays(5));
        cmd.setPickUpDate(LocalDate.now());
       commandManager.process(cmd);
    }

    @GetMapping("/restitute")
    void restitute() {
        var cmd = new ReturnCommand();

        cmd.setBorrowIds(List.of(1L));
        cmd.setMemberId(1L);

        commandManager.process(cmd);
    }

}
