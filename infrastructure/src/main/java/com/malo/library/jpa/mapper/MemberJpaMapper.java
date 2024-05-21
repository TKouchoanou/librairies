package com.malo.library.jpa.mapper;

import com.malo.library.domain.model.entities.Member;
import org.mapstruct.Mapper;
import com.malo.library.jpa.entities.MemberJpa;
@Mapper(componentModel = "spring",uses = {BorrowingJpaMapper.class})
public interface MemberJpaMapper extends GenericJpaMapper<Member, MemberJpa>{

}
