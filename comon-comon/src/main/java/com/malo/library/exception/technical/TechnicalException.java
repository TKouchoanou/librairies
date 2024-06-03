package com.malo.library.exception.technical;

import com.malo.library.exception.BorrowException;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;
@AllArgsConstructor
public class TechnicalException extends RuntimeException {
    private final TechnicalExceptionKey key;
    private List<? extends Serializable> params;
}
