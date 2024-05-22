package com.malo.library.orm.jpa.mapper;

import com.malo.library.domain.model.entities.Borrowing;
import com.malo.library.orm.jpa.entities.BorrowingJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses ={MemberJpaMapper.class} )
public interface BorrowingJpaMapper extends GenericJpaMapper<Borrowing, BorrowingJpa> {
}
