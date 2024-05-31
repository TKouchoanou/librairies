package com.malo.library.borrow;

import com.malo.library.command.CommandManager;
import com.malo.library.command.command.borrow.BorrowCommand;
import com.malo.library.command.command.borrow.ReturnCommand;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

@RestController
public class BorrowControllerTest {

    CommandManager commandManager;

    public BorrowControllerTest(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @GetMapping("/borrowBook")
    @SneakyThrows
    void borrow() {
        var cmd = new BorrowCommand();
        cmd.setBookId(1L);
        cmd.setMemberId(1L);
        cmd.setReturnedDate(LocalDate.now().plusDays(5));
        cmd.setPickUpDate(LocalDate.now());
      var res =  commandManager.process(cmd);
    }

    @GetMapping("/returnBook")
    @SneakyThrows
    void restitute() {
        var cmd = new ReturnCommand();

        cmd.setBorrowIds(Set.of(1L));
        cmd.setMemberId(1L);

        commandManager.process(cmd);
    }

}
