package com.malo.library.domain.repository;

import com.malo.library.domain.model.entities.Blocking;

import java.util.List;

public interface BlockingRepository {
    Blocking save(Blocking blocking);
   List<Blocking> findCurrentForMember(Long memberId);

   void deleteAll(List<Blocking> blockings);
}
