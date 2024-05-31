package com.malo.library.exception.business;

import lombok.Getter;
/*
* */
public class BusinessExceptionWrapper extends RuntimeException implements CheckedToUncheckedWrapper<BusinessExceptionKey,BusinessException> {
    @Getter
    BusinessException wrapped;

    public BusinessExceptionWrapper(BusinessException exception) {
        this.wrapped = exception;
    }

    /**
     * Lance le checked exception encapsulée
     *
     * @throws RuntimeException la version non vérifiée de l'exception encapsulée
     */
    @Override
    public void throwWrappedCheckedException() throws BusinessException {
     throw  this.wrapped;
    }
}
