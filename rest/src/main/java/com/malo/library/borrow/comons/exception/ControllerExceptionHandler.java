package com.malo.library.borrow.comons.exception;

import com.malo.library.exception.business.BusinessException;
import com.malo.library.exception.business.BusinessExceptionKeyEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GenericErrorDto> handleBusinessException(BusinessException ex, WebRequest request) {
        BusinessExceptionKeyEnum keyEnum = ex.getKey();

        HttpStatus httpStatus = HttpStatus.valueOf(keyEnum.getMappedHttpCode());
        GenericErrorDto errorDto = GenericErrorDto.builder(httpStatus, ex.getMessage())
                .customMessage(ex.getCustomMessage())
                .data(ex.getData())
                .errorCode(keyEnum.getMappedHttpCode())
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }
}
