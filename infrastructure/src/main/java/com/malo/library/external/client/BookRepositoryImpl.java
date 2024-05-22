package com.malo.library.external.client;

import com.malo.library.domain.model.entities.Book;
import com.malo.library.domain.model.entities.Borrowing;
import com.malo.library.domain.model.valueObject.BookAvailabilityStatus;
import com.malo.library.domain.model.valueObject.BookStatus;
import com.malo.library.domain.repository.BookRepository;
import com.malo.library.external.client.book.BookAvailabilityResult;
import com.malo.library.external.client.book.BookBookingRequest;
import com.malo.library.external.client.book.BookNotFoundException;
import com.malo.library.external.client.book.BookWebServiceClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookRepositoryImpl implements BookRepository {

    BookWebServiceClient bookWebServiceClient;

    public BookRepositoryImpl(BookWebServiceClient bookWebServiceClient) {
        this.bookWebServiceClient = bookWebServiceClient;
    }

    @Override
    public Book findById(Long bookId){
         try {
             BookAvailabilityResult availabilityResult = this.bookWebServiceClient.availabilities(bookId);
             return Book.builder()
                     .id(bookId)
                     .bookStatus(BookStatus.FOUND)
                     .availabilityStatus(availabilityResult.getQuantity() > 0 ? BookAvailabilityStatus.AVAILABLE : BookAvailabilityStatus.NOT_AVAILABLE)
                     .build();
         } catch (BookNotFoundException e) {
             return Book.builder()
                     .id(bookId)
                     .bookStatus(BookStatus.NOT_FOUND)
                     .availabilityStatus(BookAvailabilityStatus.NOT_AVAILABLE)
                     .build();
         }
    }

    @Override
    public boolean borrow(Long bookId, Integer quantity) {
        return Objects.equals(this.bookWebServiceClient.booking(bookId, BookBookingRequest.builder().bookId(bookId).quantity(quantity).build()).getBookedQuantity(), quantity);
    }

    @Override
    public void restoreOne(Long bookId) {
        this.bookWebServiceClient.restore(bookId, 1);
    }

    @Override
    public void restore(List<Borrowing> borrowings) {
       borrowings.forEach(borrowing->this.bookWebServiceClient.restore(borrowing.getBookId(), 1));
    }


}
