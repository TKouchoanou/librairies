package com.malo.library.command.handler.borrow;

import com.malo.library.command.command.borrow.ReturnCommand;
import com.malo.library.command.CommandHandler;
import com.malo.library.domain.model.entities.Blocking;
import com.malo.library.domain.model.entities.Borrowing;
import com.malo.library.domain.repository.BlockingRepository;
import com.malo.library.domain.repository.BookRepository;
import com.malo.library.domain.repository.BorrowingRepository;
import com.malo.library.exception.business.BusinessException;
import com.malo.library.exception.business.BusinessExceptionKey;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReturnCommandHandler implements CommandHandler<ReturnCommand> {
   private final BorrowingRepository borrowingRepository;
   private final BlockingRepository blockingRepository;

    private final BookRepository bookRepository;

    public ReturnCommandHandler(BorrowingRepository borrowingRepository, BlockingRepository blockingRepository, BookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.blockingRepository = blockingRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void handle(ReturnCommand command, HandlingContext handlingContext) throws BusinessException {

        List<Borrowing> ongoingBorrowings = borrowingRepository.findAllOnGoingForMember(command.getMemberId());

        Set<Long> ongoingBorrowingIds = ongoingBorrowings.stream()
                .map(Borrowing::getId)
                .collect(Collectors.toSet());
        
        Set<Long> returnedBorrowingIds = command.getBorrowIds();
       
        validateReturnedBorrowingOwnerShip(returnedBorrowingIds,ongoingBorrowingIds);

        if(hasOngoingDelayed(ongoingBorrowings) && hasReturnAllOngoingDelayed(ongoingBorrowings,returnedBorrowingIds)){

            handleDelayedBorrowings(command.getMemberId(), ongoingBorrowings);
        }

        LocalDate today = LocalDate.now();

        List<Borrowing> returnedBorrowings = markBorrowingsAsReturned(ongoingBorrowings,returnedBorrowingIds,today);

        this.borrowingRepository.saveAll(returnedBorrowings);
        this.bookRepository.restore(returnedBorrowings);
    }

    private void handleDelayedBorrowings(Long memberId, List<Borrowing> ongoingBorrowings) {
            fixMemberBlockingPeriodScope(memberId, ongoingBorrowings);
    }

    private void validateReturnedBorrowingOwnerShip(Set<Long> commandBorrowIds, Set<Long> ongoingBorrowingIds) throws BusinessException {
        if (!ongoingBorrowingIds.containsAll(commandBorrowIds)) {
            throw  new BusinessException(BusinessExceptionKey.RETURNED_BORROW_NOT_OWNED_BY_MEMBER);
        }
    }

    private boolean hasOngoingDelayed(List<Borrowing> ongoingBorrowings) {
        return ongoingBorrowings.stream().anyMatch(Borrowing::isDelayed);
    }

    private boolean hasReturnAllOngoingDelayed(List<Borrowing> ongoingBorrowings,Set<Long> returnBorrowIds) {
        return ongoingBorrowings.stream()
                .filter(Borrowing::isDelayed)
                .map(Borrowing::getId)
                .allMatch(returnBorrowIds::contains);
    }

    private List<Borrowing> markBorrowingsAsReturned(List<Borrowing> ongoingBorrowings, Set<Long> returnBorrowIds, LocalDate returnDate) {
        return ongoingBorrowings.stream()
                .filter(borrowing -> returnBorrowIds.contains(borrowing.getId()))
                .peek(borrowing -> borrowing.setEffectiveReturnedDate(returnDate))
                .map(Borrowing::markedAsReturned)
                .collect(Collectors.toList());
    }

    private void fixMemberBlockingPeriodScope(Long memberId, List<Borrowing> borrowings) {
        //TODO move to domain blocking rule service
        List<Borrowing> ongoingDelayedBorrowings = borrowings.stream().filter(Borrowing::isOnGoing).filter(Borrowing::isDelayed).toList();

        if(ongoingDelayedBorrowings.isEmpty()){
            return;
        }

        LocalDate today = LocalDate.now();

        Borrowing mostDelayedBorrowing = ongoingDelayedBorrowings.stream().min(Comparator.comparing(Borrowing::getReturnedDate)).orElseThrow();

        Period longestDelayedPeriod = Period.between(today, mostDelayedBorrowing.getReturnedDate().minusDays(1));

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plus(longestDelayedPeriod);

        List<Blocking> currentBlockings = this.blockingRepository.findAtCurrentDateForMember(memberId);

        Blocking blocking = Blocking.builder()
                .memberId(memberId)
                .endDate(endDate)
                .startDate(startDate)
                .build();

        this.blockingRepository.deleteAll(currentBlockings);
        this.blockingRepository.save(blocking);
    }
}
