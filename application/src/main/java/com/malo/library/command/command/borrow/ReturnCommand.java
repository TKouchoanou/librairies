package com.malo.library.command.command.borrow;

import com.malo.library.command.Command;
import com.malo.library.command.handler.borrow.ReturnCommandHandler;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Command.UseCase(handlers = {ReturnCommandHandler.class})
public class ReturnCommand implements Command {
    Set<Long> borrowIds;
    Long memberId;
    LocalDate effectiveReturnedDate;
}
