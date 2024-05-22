package com.malo.library.orm.jpa.mapper;

import java.util.List;

public interface GenericJpaMapper<D,E> {


    E convertToJpa(D domain);
    D convertToDomain(E entity);

    List<E> convertToJpa(List<D> domain);

    List<D> convertToDomain(List<E> entity);
}
