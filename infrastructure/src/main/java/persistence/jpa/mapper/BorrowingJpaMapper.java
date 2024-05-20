package persistence.jpa.mapper;

import domain.model.entities.Borrowing;
import org.mapstruct.Mapper;
import persistence.jpa.entities.BorrowingJpa;

@Mapper(componentModel = "spring")
public interface BorrowingJpaMapper extends GenericJpaMapper<Borrowing, BorrowingJpa> {
}
