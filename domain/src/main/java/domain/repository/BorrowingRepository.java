package domain.repository;

import domain.model.entities.Borrowing;

import java.util.List;

public interface BorrowingRepository {
    Integer countOnGoingForMember(Long memberId);
    List<Borrowing> findAllOnGoing();

    List<Borrowing> findAllOnGoingForMember(Long memberId);

    List<Borrowing> findAllDelayed();

    List<Borrowing> findAllByIds(List<Long> id);

    Borrowing save(Borrowing borrowing);
    void saveAll(List<Borrowing> borrowings);
}
