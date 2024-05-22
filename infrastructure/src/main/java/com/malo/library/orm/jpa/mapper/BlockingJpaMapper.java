package com.malo.library.orm.jpa.mapper;

import com.malo.library.domain.model.entities.Blocking;
import com.malo.library.orm.jpa.entities.BlockingJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlockingJpaMapper extends GenericJpaMapper<Blocking, BlockingJpa>{
}
