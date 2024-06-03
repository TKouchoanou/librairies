package com.malo.library.domain.repository;

import com.malo.library.domain.model.entities.Blocking;

import java.util.List;

public interface BlockingRepository {
    Long save(Blocking blocking);
   List<Blocking> findAtCurrentDateForMember(Long memberId);

   void deleteAll(List<Blocking> blockings);
}
