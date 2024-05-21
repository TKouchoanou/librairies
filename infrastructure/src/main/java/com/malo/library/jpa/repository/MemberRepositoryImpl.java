package com.malo.library.jpa.repository;

import com.malo.library.jpa.mapper.MemberJpaMapper;
import com.malo.library.domain.model.entities.Member;
import com.malo.library.domain.repository.MemberRepository;
import org.springframework.stereotype.Repository;
import com.malo.library.jpa.entities.MemberJpa;
import com.malo.library.jpa.repository.jpa.MemberJpaRepository;
@Repository
public class MemberRepositoryImpl extends GenericCrudRepository<Member, MemberJpa,Long,MemberJpaRepository, MemberJpaMapper> implements MemberRepository {
    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository, MemberJpaMapper memberJpaMapper) {
        super(memberJpaMapper,memberJpaRepository);
    }
}
