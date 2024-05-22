package com.malo.library.domain.repository;


import com.malo.library.domain.model.entities.Book;
import com.malo.library.domain.model.entities.Borrowing;

import java.util.List;

public interface BookRepository {

    Book findById(Long bookId);

    boolean borrow(Long bookId, Integer quantity);

    default boolean borrowOne(Long bookId) {
        return this.borrow(bookId, 1);
    }

    void restoreOne(Long bookId);

    void restore(List<Borrowing> borrowings);


}
