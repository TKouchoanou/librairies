package command.command.borrow;

import command.command.Command;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class BlockedDelayedBorrowMemberCommand implements Command {
    LocalDate date;
}
