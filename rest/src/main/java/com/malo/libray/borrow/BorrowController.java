package com.malo.libray.borrow;

import command.command.borrow.BorrowCommand;
import command.handler.borrow.BorrowCommandHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class BorrowController {

    public BorrowController(BorrowCommandHandler borrowCommandHandler) {
        this.borrowCommandHandler = borrowCommandHandler;
    }

    BorrowCommandHandler borrowCommandHandler;
    @GetMapping("/borrow")
    void borrow(){
        var cmd = new BorrowCommand();
        cmd.setBookId(1L);
        cmd.setMemberId(1L);
        cmd.setReturnedDate(LocalDate.now().plusDays(5));
        cmd.setPickUpDate(LocalDate.now());

        borrowCommandHandler.handle(cmd);
    }

}
