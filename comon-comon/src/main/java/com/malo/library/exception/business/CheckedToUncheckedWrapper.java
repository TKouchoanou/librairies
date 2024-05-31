package com.malo.library.exception.business;

import com.malo.library.exception.BorrowException;
import com.malo.library.exception.BorrowExceptionKey;

public interface CheckedToUncheckedWrapper<T extends BorrowExceptionKey,E extends BorrowException<T>>{

    public E getWrapped() ;

   void throwWrappedCheckedException() throws Exception;
}
