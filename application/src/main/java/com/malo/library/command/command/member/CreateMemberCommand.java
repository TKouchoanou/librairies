package com.malo.library.command.command.member;

import com.malo.library.command.Command;
import com.malo.library.command.handler.member.CreateMemberCommandHandler;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Command.UseCase(handlers = {CreateMemberCommandHandler.class})
public class CreateMemberCommand implements Command {
    Long id;

}
