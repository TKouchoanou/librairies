package com.malo.library.persistence.jpa.mapper;

import com.malo.library.domain.model.entities.Member;
import com.malo.library.persistence.jpa.entities.MemberJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {BorrowingJpaMapper.class})
public interface MemberJpaMapper extends GenericJpaMapper<Member, MemberJpa>{

}
