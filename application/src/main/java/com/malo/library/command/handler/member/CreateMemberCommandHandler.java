package com.malo.library.command.handler.member;

import com.malo.library.command.CommandHandler;
import com.malo.library.command.command.member.CreateMemberCommand;
import com.malo.library.domain.model.entities.Member;
import com.malo.library.domain.model.valueObject.MemberAccountStatus;
import com.malo.library.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateMemberCommandHandler implements CommandHandler<CreateMemberCommand> {

   MemberRepository memberRepository;

    public CreateMemberCommandHandler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void handle(CreateMemberCommand command, HandlingContext handlingContext) {
       this.memberRepository.save(Member.builder()
               .id(command.getId())
               .status(MemberAccountStatus.ACTIVE)
               .build());
    }
}
