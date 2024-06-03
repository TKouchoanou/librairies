
package com.malo.library.exception.business;


import com.malo.library.exception.BorrowException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;

@Getter
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("unused")
public class BusinessException extends Exception implements BorrowException<BusinessExceptionKey> {
    @Serial
    private static final long serialVersionUID = 1L;

    private final BusinessExceptionKey key;

    private  Object data;

    private  String customMessage;

    public BusinessException(BusinessExceptionKey key, Object ...errorParams) {
        this(key,null,"",errorParams);
    }

    public BusinessException(BusinessExceptionKey key) {
        this.key =key;
    }

    public BusinessException(Object data, BusinessExceptionKey key, Object ...errorParams) {
        this(key,data, "",errorParams);
    }

    public BusinessException(BusinessExceptionKey key, Object data, String customMessage, Object ...errorParams) {
        super(String.format(key.getMessage(),errorParams));
        this.key = key;
        this.data = data;
        this.customMessage = customMessage;
    }

    public BusinessException(BusinessExceptionKey key, Object data, String customMessage, Throwable cause, Object ...errorParams) {
        super(String.format(key.getMessage(),errorParams), cause);
        this.key = key;
        this.data = data;
        this.customMessage = customMessage;
    }
    public BusinessException(BusinessExceptionKey key, Object data, Throwable cause, Object ...errorParams) {
        super(String.format(key.getMessage(),errorParams), cause);
        this.key = key;
        this.data = data;
    }

}
