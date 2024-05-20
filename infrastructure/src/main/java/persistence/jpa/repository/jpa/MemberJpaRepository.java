package persistence.jpa.repository.jpa;

import domain.model.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import persistence.jpa.entities.MemberJpa;

public interface MemberJpaRepository  extends JpaRepository<MemberJpa,Long> {
}
