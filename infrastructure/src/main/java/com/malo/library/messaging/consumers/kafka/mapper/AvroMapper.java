package com.malo.library.messaging.consumers.kafka.mapper;

import java.util.List;

public interface AvroMapper<T,A>{

    A convertToAvro(T dto);
    T convertToDto(A entity);

    List<T> convertToDto(List<A> entities);

    List<A> convertToAvro(List<T> dto);


}
