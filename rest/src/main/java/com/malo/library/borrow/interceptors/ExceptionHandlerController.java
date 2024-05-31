package com.malo.library.borrow.interceptors;

import com.malo.library.borrow.comons.exception.GenericErrorDto;
import com.malo.library.exception.business.BusinessException;
import com.malo.library.exception.business.BusinessExceptionKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GenericErrorDto> handleBusinessException(BusinessException ex, WebRequest request) {
        BusinessExceptionKey key = ex.getKey();

        HttpStatus httpStatus = HttpStatus.valueOf(key.getMappedHttpCode());
        GenericErrorDto errorDto = GenericErrorDto.builder(httpStatus, ex.getMessage())
                .customMessage(ex.getCustomMessage())
                .data(ex.getData())
                .errorCode(key.name())
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }


}
