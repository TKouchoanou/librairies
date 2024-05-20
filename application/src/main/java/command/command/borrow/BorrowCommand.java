package command.command.borrow;

import command.command.Command;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowCommand implements Command {

   public Long memberId;

   public Long bookId;

   public LocalDate pickUpDate;

   public LocalDate returnedDate;

}
