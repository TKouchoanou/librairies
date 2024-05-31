package com.malo.library.borrow.comons.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public class GenericErrorDto {
    private LocalDateTime createdDate ;
    private HttpStatus httpStatus;
    String message;

    String customMessage;

    private  Object data;

    private String errorCode;

    @Builder(builderMethodName = "hiddenBuilder")
    public GenericErrorDto(LocalDateTime createdDate, HttpStatus httpStatus, String message, String customMessage, Object data, String errorCode) {
        this.createdDate = (createdDate != null) ? createdDate : LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.message = message;
        this.customMessage = customMessage;
        this.data = data;
        this.errorCode = errorCode;
    }
    public static GenericErrorDtoBuilder builder(HttpStatus httpStatus, String message) {
        return hiddenBuilder()
                .httpStatus(httpStatus)
                .message(message);
    }

}
