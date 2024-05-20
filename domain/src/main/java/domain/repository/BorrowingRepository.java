package domain.repository;

import domain.model.entities.Borrowing;

import java.util.List;

public interface BorrowingRepository {
    Integer countOnGoingForMember(Long memberId);
    List<Borrowing> findAllOnGoing();

    Borrowing save(Borrowing borrowing);
}
