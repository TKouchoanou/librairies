package com.malo.library.domain.repository;

import com.malo.library.domain.model.entities.Member;

import java.util.Optional;

public interface MemberRepository {
   Optional<Member> findById(Long id);
    Long save(Member member);

}
