package com.malo.library.exception.business;

import com.malo.library.exception.BorrowExceptionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessExceptionKeyEnum implements BorrowExceptionKey {

    MEMBER_NOT_FOUND(404),
    MEMBER_IS_INACTIVE(403),
    MEMBER_IS_BLOCKED(403),
    BOOK_NOT_FOUND(404),
    BOOK_NOT_AVAILABLE(409),
    BOOK_ALREADY_BORROWED_BY_MEMBER(409),
    MAX_BORROW_LIMIT_REACHED(403),
    BOOK_BORROW_FAILED(500),
    INCOHERENT_COMMAND(400),
    RETURNED_BORROW_NOT_OWNED_BY_MEMBER(400);
    final int mappedHttpCode;

}
