package persistence.jpa.repository.jpa;

import domain.model.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import persistence.jpa.entities.MemberJpa;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberJpaRepository  extends JpaRepository<MemberJpa,Long> {
    @Override
    default Optional<MemberJpa> findById(Long id){
        return this.findByIdAndBlockingDate(id,LocalDateTime.now());
    }

    @Query("select m from MemberJpa m join m.currentBlockings b " +
            "where m.id = :id and :date between b.startDate and b.endDate")
    Optional<MemberJpa> findByIdAndBlockingDate(Long id, LocalDateTime date);
}
