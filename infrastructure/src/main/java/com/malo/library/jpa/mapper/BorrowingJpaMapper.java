package com.malo.library.jpa.mapper;

import com.malo.library.domain.model.entities.Borrowing;
import org.mapstruct.Mapper;
import com.malo.library.jpa.entities.BorrowingJpa;

@Mapper(componentModel = "spring",uses ={MemberJpaMapper.class} )
public interface BorrowingJpaMapper extends GenericJpaMapper<Borrowing, BorrowingJpa> {
}
