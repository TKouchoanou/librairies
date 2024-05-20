package persistence.jpa.repository;

import domain.model.entities.Member;
import domain.repository.MemberRepository;
import org.springframework.stereotype.Repository;
import persistence.jpa.entities.MemberJpa;
import persistence.jpa.mapper.MemberJpaMapper;
import persistence.jpa.repository.jpa.MemberJpaRepository;
@Repository
public class MemberRepositoryImpl extends GenericCrudRepository<Member, MemberJpa,Long,MemberJpaRepository, MemberJpaMapper> implements MemberRepository {
    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository, MemberJpaMapper memberJpaMapper) {
        super(memberJpaMapper,memberJpaRepository);
    }
}
