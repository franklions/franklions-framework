package com.franklions.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotFoundResourceException extends RuntimeException {
    private ErrorResult errorResult;

    public NotFoundResourceException(ErrorResult errorResult) {
        this.errorResult = errorResult;
    }

    public NotFoundResourceException(String message, ErrorResult errorResult) {
        super(message);
        this.errorResult = errorResult;
    }

    public NotFoundResourceException(String message, Throwable cause, ErrorResult errorResult) {
        super(message, cause);
        this.errorResult = errorResult;
    }

    public NotFoundResourceException(Object[] error){
        super(error[1].toString());
        errorResult = new ErrorResult();
        this.errorResult.setErrorCode(Integer.valueOf(error[0].toString()));
        this.errorResult.setErrorMessage(error[1].toString());
    }
    public NotFoundResourceException(String message,Object[] error){
        super(message);
        errorResult = new ErrorResult();
        this.errorResult.setErrorCode(Integer.valueOf(error[0].toString()));
        this.errorResult.setErrorMessage(message);
    }
}
