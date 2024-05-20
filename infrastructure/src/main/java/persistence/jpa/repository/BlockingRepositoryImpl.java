package persistence.jpa.repository;

import domain.model.entities.Blocking;
import domain.repository.BlockingRepository;
import org.springframework.stereotype.Repository;
import persistence.jpa.entities.BlockingJpa;
import persistence.jpa.mapper.BlockingJpaMapper;
import persistence.jpa.repository.jpa.BlockingJpaRepository;

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
