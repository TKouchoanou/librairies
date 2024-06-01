package com.malo.library.persistence.jpa.repository;

import com.malo.library.persistence.jpa.mapper.MemberJpaMapper;
import com.malo.library.domain.model.entities.Member;
import com.malo.library.domain.repository.MemberRepository;
import com.malo.library.persistence.jpa.repository.jpa.MemberJpaRepository;
import com.malo.library.persistence.jpa.entities.MemberJpa;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl extends GenericCrudRepository<Member, MemberJpa,Long, MemberJpaRepository, MemberJpaMapper> implements MemberRepository {
    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository, MemberJpaMapper memberJpaMapper) {
        super(memberJpaMapper,memberJpaRepository);
    }
}
