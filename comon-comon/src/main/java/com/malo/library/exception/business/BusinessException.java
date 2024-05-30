
package com.malo.library.exception.business;


import com.malo.library.exception.BorrowException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("unused")
public class BusinessException extends Exception implements BorrowException<BusinessExceptionKeyEnum> {
    @Serial
    private static final long serialVersionUID = 1L;

    private final BusinessExceptionKeyEnum key;

    private final Object data;

    private  String customMessage;

    public BusinessException(BusinessExceptionKeyEnum key,Object ...errorParams) {
        this(key,null,"",errorParams);
    }

    public BusinessException(BusinessExceptionKeyEnum key) {
        this(key,null,"",List.of());
    }

    public BusinessException(BusinessExceptionKeyEnum key, Object data,Object ...errorParams) {
        this(key,data, "",errorParams);
    }

    public BusinessException(BusinessExceptionKeyEnum key, Object data, String customMessage,Object ...errorParams) {
        super(String.format(key.getMessage(),errorParams));
        this.key = key;
        this.data = data;
        this.customMessage = customMessage;
    }

    public BusinessException(BusinessExceptionKeyEnum key, Object data, String customMessage,Throwable cause,Object ...errorParams) {
        super(String.format(key.getMessage(),errorParams), cause);
        this.key = key;
        this.data = data;
        this.customMessage = customMessage;
    }
    public BusinessException(BusinessExceptionKeyEnum key, Object data,Throwable cause,Object ...errorParams) {
        super(String.format(key.getMessage(),errorParams), cause);
        this.key = key;
        this.data = data;
    }

}
