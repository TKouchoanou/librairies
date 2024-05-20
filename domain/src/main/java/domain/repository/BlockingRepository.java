package domain.repository;

import domain.model.entities.Blocking;

import java.util.List;

public interface BlockingRepository {
    Blocking save(Blocking blocking);
   List<Blocking> findCurrentForMember(Long memberId);

   void deleteAll(List<Blocking> blockings);
}
