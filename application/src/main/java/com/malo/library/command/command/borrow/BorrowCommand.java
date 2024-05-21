package com.malo.library.command.command.borrow;

import com.malo.library.command.Command;
import com.malo.library.command.handler.borrow.BorrowCommandHandler;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Command.Usecase(handlers = {BorrowCommandHandler.class})
public class BorrowCommand implements Command {

   public Long memberId;

   public Long bookId;

   public LocalDate pickUpDate;

   public LocalDate returnedDate;

}
