package command.command.borrow;

import command.command.Command;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
@Getter
public class ReturnCommand implements Command {
    List<Long> borrowIds;
    Long memberId;
    LocalDate effectiveReturnedDate;
}
