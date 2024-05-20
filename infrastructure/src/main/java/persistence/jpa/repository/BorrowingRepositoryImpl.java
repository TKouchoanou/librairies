package persistence.jpa.repository;

import domain.model.entities.Borrowing;
import domain.model.valueObject.BorrowStatus;
import domain.model.valueObject.ReturnStatus;
import domain.repository.BorrowingRepository;
import org.springframework.stereotype.Repository;
import persistence.jpa.entities.BorrowingJpa;
import persistence.jpa.mapper.BorrowingJpaMapper;
import persistence.jpa.repository.jpa.BorrowingJpaRepository;

import java.util.List;
@Repository
public class BorrowingRepositoryImpl  extends GenericCrudRepository<Borrowing, BorrowingJpa,Long,BorrowingJpaRepository, BorrowingJpaMapper> implements BorrowingRepository {

    public BorrowingRepositoryImpl(BorrowingJpaMapper mapper, BorrowingJpaRepository repository) {
        super(mapper, repository);
    }

    @Override
    public Integer countOnGoingForMember(Long memberId) {
        return getRepository().countByMemberIdAndBorrowStatus(memberId, BorrowStatus.ONGOING);
    }

    @Override
    public List<Borrowing> findAllOnGoing() {
        return this.getMapper().convertToDomain(getRepository().findByBorrowStatus(BorrowStatus.ONGOING));
    }

    @Override
    public List<Borrowing> findAllOnGoingForMember(Long memberId) {
        return this.getMapper().convertToDomain(getRepository().findByMemberIdAndBorrowStatus(memberId,BorrowStatus.ONGOING));
    }

    @Override
    public List<Borrowing> findAllDelayed() {
        return this.getMapper().convertToDomain(this.getRepository().findByBorrowStatusAndReturnStatus(BorrowStatus.ONGOING, ReturnStatus.DELAYED));
    }
}
