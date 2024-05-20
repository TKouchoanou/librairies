package persistence.jpa.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import persistence.jpa.entities.BlockingJpa;

import java.time.LocalDate;
import java.util.List;

public interface BlockingJpaRepository extends JpaRepository<BlockingJpa,Long> {

    @Query("select b from BlockingJpa b where b.memberId = :memberId and :date between b.startDate and b.endDate")
    List<BlockingJpa> findBlockingsByMemberIdAndStartDateBeforeAndEndDateAfter(@Param("memberId") Long memberId, @Param("date") LocalDate date);
}
