package com.malo.library.exception.business;

import com.malo.library.exception.BorrowException;
import com.malo.library.exception.BorrowExceptionKey;

public interface WrappedCheckedToUncheck <T extends BorrowExceptionKey,E extends BorrowException<T>>{

    public E getCause() ;

   void throwException () throws Exception;
}
