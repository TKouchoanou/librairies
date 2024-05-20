package domain.repository;

import domain.model.entities.Book;
import domain.model.valueObject.BookAvailabilityStatus;
import domain.model.valueObject.BookStatus;

public interface BookRepository {

  default   Book findById(Long bookId){
      return  Book.builder()
              .id(bookId)
              .bookStatus(BookStatus.FOUND)
              .availabilityStatus(BookAvailabilityStatus.AVAILABLE)
              .build();
  };

}
