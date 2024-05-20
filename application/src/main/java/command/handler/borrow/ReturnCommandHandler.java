package command.handler.borrow;

import command.command.borrow.ReturnCommand;
import command.handler.CommandHandler;
import domain.model.entities.Blocking;
import domain.model.entities.Borrowing;
import domain.repository.BlockingRepository;
import domain.repository.BorrowingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReturnCommandHandler implements CommandHandler<ReturnCommand> {
    BorrowingRepository borrowingRepository;
    BlockingRepository blockingRepository;

    public ReturnCommandHandler(BorrowingRepository borrowingRepository, BlockingRepository blockingRepository) {
        this.borrowingRepository = borrowingRepository;
        this.blockingRepository = blockingRepository;
    }

    @Override
    public void handle(ReturnCommand command) {
       List<Borrowing> borrowingList = borrowingRepository.findAllOnGoingForMember(command.getMemberId());

        Set<Long> borrowingIds = borrowingList.stream().map(Borrowing::getId).collect(Collectors.toSet());

        if(borrowingIds.containsAll(command.getBorrowIds())){
            throw  new RuntimeException(" incoherente command check borrows owned for user and on going");
        }

        List<Borrowing> returnedBorrowings = borrowingList.stream()
                .filter(borrowing ->borrowingIds.contains(borrowing.getId()))
                .map(Borrowing::markedAsReturned)
                .collect(Collectors.toList());

       this.borrowingRepository.saveAll(returnedBorrowings);

       List<Borrowing> notReturnedBorrowings = borrowingList.stream()
               .filter(borrowing -> !command.getBorrowIds().contains(borrowing.getId()))
               .collect(Collectors.toList());;

        boolean hasOngoingDelayed = !notReturnedBorrowings.isEmpty() && notReturnedBorrowings.stream().anyMatch(Borrowing::isDelayed);

        if(!hasOngoingDelayed){
            this.blockedMember(command.getMemberId(), notReturnedBorrowings);
        }

    }

    void blockedMember(Long memberId, List<Borrowing> borrowings) {
        //domain service methode
        LocalDate today = LocalDate.now();
        Borrowing mostDelayedBorrowing = borrowings.stream().min(Comparator.comparing(Borrowing::getReturnedDate)).orElseThrow();
        Period mostDelayedPeriod = Period.between(today,mostDelayedBorrowing.getReturnedDate().minusDays(1));

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plus(mostDelayedPeriod);

        List<Blocking> currentBlockings = this.blockingRepository.findCurrentForMember(memberId);

        Blocking blocking = Blocking.builder()
                .memberId(memberId)
                .endDate(endDate)
                .startDate(startDate)
                .build();

        this.blockingRepository.deleteAll(currentBlockings);
        this.blockingRepository.save(blocking);
    }
}
