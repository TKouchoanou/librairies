package com.malo.library.jpa.repository;

import com.malo.library.domain.model.entities.Blocking;
import com.malo.library.domain.repository.BlockingRepository;
import org.springframework.stereotype.Repository;
import com.malo.library.jpa.entities.BlockingJpa;
import com.malo.library.jpa.mapper.BlockingJpaMapper;
import com.malo.library.jpa.repository.jpa.BlockingJpaRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BlockingRepositoryImpl extends GenericCrudRepository<Blocking, BlockingJpa,Long, BlockingJpaRepository, BlockingJpaMapper> implements BlockingRepository {
    public BlockingRepositoryImpl(BlockingJpaMapper mapper, BlockingJpaRepository repository) {
        super(mapper, repository);
    }

    @Override
    public List<Blocking> findCurrentForMember(Long memberId) {
        List<BlockingJpa> blockings = getRepository().findBlockingsByMemberIdAndStartDateBeforeAndEndDateAfter(memberId, LocalDate.now());
        return this.getMapper().convertToDomain(blockings);
    }
}
