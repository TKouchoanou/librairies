package com.malo.library.domain.repository;

import com.malo.library.domain.model.entities.Borrowing;

import java.util.List;

public interface BorrowingRepository {
    Integer countOnGoingForMember(Long memberId);
    List<Borrowing> findAllOnGoing();
   Boolean isBookCurrentlyBorrowedByMember(Long bookId,Long memberId);

    List<Borrowing> findAllOnGoingForMember(Long memberId);

    List<Borrowing> findAllDelayed();

    List<Borrowing> findAllByIds(List<Long> id);

    Long save(Borrowing borrowing);
    void saveAll(List<Borrowing> borrowings);
}
