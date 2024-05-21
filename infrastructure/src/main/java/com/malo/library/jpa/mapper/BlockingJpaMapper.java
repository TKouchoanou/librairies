package com.malo.library.jpa.mapper;

import com.malo.library.domain.model.entities.Blocking;
import com.malo.library.jpa.entities.BlockingJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlockingJpaMapper extends GenericJpaMapper<Blocking, BlockingJpa>{
}
