package com.malo.library.orm.jpa.repository;

import com.malo.library.orm.jpa.mapper.MemberJpaMapper;
import com.malo.library.domain.model.entities.Member;
import com.malo.library.domain.repository.MemberRepository;
import com.malo.library.orm.jpa.repository.jpa.MemberJpaRepository;
import org.springframework.stereotype.Repository;
import com.malo.library.orm.jpa.entities.MemberJpa;

@Repository
public class MemberRepositoryImpl extends GenericCrudRepository<Member, MemberJpa,Long, MemberJpaRepository, MemberJpaMapper> implements MemberRepository {
    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository, MemberJpaMapper memberJpaMapper) {
        super(memberJpaMapper,memberJpaRepository);
    }
}
