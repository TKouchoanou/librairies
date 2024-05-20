package persistence.jpa.repository.jpa;

import domain.model.valueObject.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import persistence.jpa.entities.BorrowingJpa;
import persistence.jpa.entities.MemberJpa;

import java.util.List;

public interface BorrowingJpaRepository extends JpaRepository<BorrowingJpa,Long> {
    Integer countByMemberIdAndBorrowStatus(Long memberId, BorrowStatus borrowStatus);
    List<BorrowingJpa> findByBorrowStatus(BorrowStatus borrowStatus);
}
