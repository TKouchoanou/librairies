package com.malo.library.exception.business;

import lombok.Getter;

public class BusinessExceptionWrapped  extends RuntimeException implements WrappedCheckedToUncheck<BusinessExceptionKeyEnum,BusinessException>{
    @Getter
    BusinessException cause;

    public BusinessExceptionWrapped(BusinessException exception) {
        this.cause = exception;
    }


    @Override
    public void throwException() throws BusinessException {
     throw  this.cause;
    }
}
