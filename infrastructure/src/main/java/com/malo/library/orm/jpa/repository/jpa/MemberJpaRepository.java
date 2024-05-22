package com.malo.library.orm.jpa.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.malo.library.orm.jpa.entities.MemberJpa;

import java.util.Optional;

public interface MemberJpaRepository  extends JpaRepository<MemberJpa,Long> {
    @Override
    @Query("select m from MemberJpa m left join m.blockings b where m.id = :id ")
     Optional<MemberJpa> findById(Long id);

}
