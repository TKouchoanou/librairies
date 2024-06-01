package com.malo.library.persistence.jpa.repository.jpa;

import com.malo.library.persistence.jpa.entities.MemberJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberJpaRepository  extends JpaRepository<MemberJpa,Long> {
    @Override
    @Query("select m from MemberJpa m left join m.blockings b where m.id = :id ")
     Optional<MemberJpa> findById(Long id);

}
