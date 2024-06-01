package com.malo.library.persistence.jpa.repository;

import com.malo.library.domain.model.entities.Blocking;
import com.malo.library.domain.repository.BlockingRepository;
import com.malo.library.persistence.jpa.entities.BlockingJpa;
import com.malo.library.persistence.jpa.mapper.BlockingJpaMapper;
import com.malo.library.persistence.jpa.repository.jpa.BlockingJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BlockingRepositoryImpl extends GenericCrudRepository<Blocking, BlockingJpa,Long, BlockingJpaRepository, BlockingJpaMapper> implements BlockingRepository {
    public BlockingRepositoryImpl(BlockingJpaMapper mapper, BlockingJpaRepository repository) {
        super(mapper, repository);
    }

    @Override
    public List<Blocking> findAtCurrentDateForMember(Long memberId) {
        List<BlockingJpa> blockings = getRepository().findBlockingsByMemberIdAndStartDateBeforeAndEndDateAfter(memberId, LocalDateTime.now());
        return this.getMapper().convertToDomain(blockings);
    }
}
