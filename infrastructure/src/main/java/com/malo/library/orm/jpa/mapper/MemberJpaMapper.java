package com.malo.library.orm.jpa.mapper;

import com.malo.library.domain.model.entities.Member;
import org.mapstruct.Mapper;
import com.malo.library.orm.jpa.entities.MemberJpa;
@Mapper(componentModel = "spring",uses = {BorrowingJpaMapper.class})
public interface MemberJpaMapper extends GenericJpaMapper<Member, MemberJpa>{

}
