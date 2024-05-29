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
@Command.UseCase(handlers = {BorrowCommandHandler.class})
public class BorrowCommand implements Command {
   Long id ;

   public Long memberId;

   public Long bookId;

   public LocalDate pickUpDate;

   public LocalDate returnedDate;

   @Override
   public boolean checkValidityAfterHandling(StringBuilder violationsMessage) {
      return id!=null && id!=0;
   }
}
