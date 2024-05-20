package domain.repository;

import domain.model.entities.Member;

import java.util.Optional;

public interface MemberRepository {
   Optional<Member> findById(Long id);
    Member save(Member member);

}
