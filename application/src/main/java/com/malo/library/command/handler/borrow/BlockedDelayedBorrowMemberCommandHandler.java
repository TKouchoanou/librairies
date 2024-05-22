package com.malo.library.command.handler.borrow;

import com.malo.library.command.command.borrow.BlockedDelayedBorrowMemberCommand;
import com.malo.library.command.CommandHandler;
import com.malo.library.domain.model.entities.Blocking;
import com.malo.library.domain.model.entities.Borrowing;
import com.malo.library.domain.repository.BlockingRepository;
import com.malo.library.domain.repository.BorrowingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BlockedDelayedBorrowMemberCommandHandler implements CommandHandler<BlockedDelayedBorrowMemberCommand> {

    final static Period DEFAULT_BLOCKING_PERIOD = Period.ofDays(365); //to move to borrowReference common

    BorrowingRepository borrowingRepository;
    BlockingRepository blockingRepository;

    public BlockedDelayedBorrowMemberCommandHandler(BorrowingRepository borrowingRepository, BlockingRepository blockingRepository) {
        this.borrowingRepository = borrowingRepository;
        this.blockingRepository = blockingRepository;
    }


    @Override
    public void handle(BlockedDelayedBorrowMemberCommand command, HandlingContext handlingContext) {

        Predicate<Borrowing> toDayDelayedPredicate = dateDelayedPredicate(LocalDate.now());

        Map<Long, List<Borrowing>> delayedBorrowingsByMember = this.borrowingRepository.findAllOnGoing()
                .stream()
                .filter(toDayDelayedPredicate)
                .collect(Collectors.groupingBy(Borrowing::getMemberId, Collectors.toList()));

        delayedBorrowingsByMember.forEach(this::markDelayedAndBlockedMember);
    }

    Predicate<Borrowing> dateDelayedPredicate(LocalDate date) {
        return (Borrowing borrowing) -> borrowing.getReturnedDate().isBefore(date);
    }

    void markDelayedAndBlockedMember(Long memberId, List<Borrowing> borrowings) {
        Blocking blocking = Blocking.builder()
                .memberId(memberId)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plus(DEFAULT_BLOCKING_PERIOD))
                .build();
        borrowings.forEach(Borrowing::markedAsDelayed);
        this.borrowingRepository.saveAll(borrowings);
        this.blockingRepository.save(blocking);
    }

    /*

      void blockedMember(Long memberId, List<Borrowing> borrowings) {
        LocalDate today = LocalDate.now();
        Borrowing moreDelayed = borrowings.stream().min(Comparator.comparing(Borrowing::getReturnedDate)).orElseThrow();
        Period maxDelayedPeriod = Period.between(today,moreDelayed.getReturnedDate().minusDays(1));

        if(borrowings.stream().allMatch(borrowing -> borrowing.getBorrowStatus().equals(BorrowStatus.RETURNED))){

        }

         List<Blocking> currentBlockings = this.blockingRepository.findCurrentForMember(memberId);

        Blocking blocking = Blocking.builder().memberId(memberId).endDate(today).build();

        this.blockingRepository.save()
    }

    */
}
