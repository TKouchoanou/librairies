package com.malo.library.domain.repository;


import com.malo.library.domain.model.entities.Book;
import com.malo.library.domain.model.valueObject.BookAvailabilityStatus;
import com.malo.library.domain.model.valueObject.BookStatus;

public interface BookRepository {

   Book findById(Long bookId);

}
