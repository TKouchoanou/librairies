package com.malo.library.external.client;

import com.malo.library.domain.model.entities.Book;
import com.malo.library.domain.model.valueObject.BookAvailabilityStatus;
import com.malo.library.domain.model.valueObject.BookStatus;
import com.malo.library.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookWebServiceClient implements BookRepository {
     @Override
    public Book findById(Long bookId){
        return  Book.builder()
                .id(bookId)
                .bookStatus(BookStatus.FOUND)
                .availabilityStatus(BookAvailabilityStatus.AVAILABLE)
                .build();
    };
}
