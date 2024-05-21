package com.malo.library.jpa.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.malo.library.jpa.entities.MemberJpa;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberJpaRepository  extends JpaRepository<MemberJpa,Long> {
    @Override
    @Query("select m from MemberJpa m left join m.blockings b where m.id = :id ")
     Optional<MemberJpa> findById(Long id);

}
