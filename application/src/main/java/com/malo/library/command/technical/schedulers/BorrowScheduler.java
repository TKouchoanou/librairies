package com.malo.library.command.technical.schedulers;

import com.malo.library.command.CommandManager;
import com.malo.library.command.command.borrow.BlockedDelayedBorrowMemberCommand;
import com.malo.library.exception.business.BusinessException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class BorrowScheduler {
    CommandManager commandManager;

    public BorrowScheduler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }




   @Scheduled(fixedDelayString = "${member.blockingDelayedMember.scheduling.delay}")
    void scheduleBlockingDelayedMember(){
        try {
            commandManager.process(new BlockedDelayedBorrowMemberCommand());
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }
}
