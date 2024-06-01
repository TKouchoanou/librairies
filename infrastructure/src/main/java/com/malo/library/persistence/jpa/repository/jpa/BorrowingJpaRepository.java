package com.malo.library.persistence.jpa.repository.jpa;

import com.malo.library.domain.model.valueObject.BorrowStatus;
import com.malo.library.domain.model.valueObject.ReturnStatus;
import com.malo.library.persistence.jpa.entities.BorrowingJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingJpaRepository extends JpaRepository<BorrowingJpa,Long> {
    Integer countByMemberIdAndBorrowStatus(Long memberId, BorrowStatus borrowStatus);

    Boolean existsByBookIdAndMemberIdAndBorrowStatus(Long bookId,Long memberId, BorrowStatus borrowStatus);
    Integer countByBookIdAndMemberIdAndBorrowStatus(Long bookId,Long memberId, BorrowStatus borrowStatus);
    List<BorrowingJpa> findByBorrowStatus(BorrowStatus borrowStatus);
    List<BorrowingJpa> findByMemberIdAndBorrowStatus(Long memberId,BorrowStatus borrowStatus);
    List<BorrowingJpa> findByBorrowStatusAndReturnStatus(BorrowStatus borrowStatus, ReturnStatus returnStatus);
}
