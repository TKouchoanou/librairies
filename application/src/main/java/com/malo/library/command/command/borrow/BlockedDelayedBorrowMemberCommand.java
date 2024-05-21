package com.malo.library.command.command.borrow;

import com.malo.library.command.Command;
import com.malo.library.command.handler.borrow.BlockedDelayedBorrowMemberCommandHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@Command.Usecase(handlers = {BlockedDelayedBorrowMemberCommandHandler.class})
public class BlockedDelayedBorrowMemberCommand implements Command {
    LocalDate date;
}
