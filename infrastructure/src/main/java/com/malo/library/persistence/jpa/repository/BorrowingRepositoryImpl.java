package com.malo.library.persistence.jpa.repository;

import com.malo.library.persistence.jpa.mapper.BorrowingJpaMapper;
import com.malo.library.domain.model.entities.Borrowing;
import com.malo.library.domain.model.valueObject.BorrowStatus;
import com.malo.library.domain.model.valueObject.ReturnStatus;
import com.malo.library.domain.repository.BorrowingRepository;
import com.malo.library.persistence.jpa.entities.BorrowingJpa;
import com.malo.library.persistence.jpa.repository.jpa.BorrowingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BorrowingRepositoryImpl  extends GenericCrudRepository<Borrowing, BorrowingJpa,Long, BorrowingJpaRepository, BorrowingJpaMapper> implements BorrowingRepository {

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
    public Boolean isBookCurrentlyBorrowedByMember(Long bookId, Long memberId) {
        return this.getRepository().existsByBookIdAndMemberIdAndBorrowStatus(bookId,memberId,BorrowStatus.ONGOING);
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
