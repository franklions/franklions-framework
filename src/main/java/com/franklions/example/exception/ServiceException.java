package com.franklions.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    private ErrorResult errorResult;

    public ServiceException(ErrorResult errorResult) {
        this.errorResult = errorResult;
    }

    public ServiceException(String message, ErrorResult errorResult) {
        super(message);
        this.errorResult = errorResult;
    }

    public ServiceException(String message, Throwable cause, ErrorResult errorResult) {
        super(message, cause);
        this.errorResult = errorResult;
    }

    public ServiceException(Object[] error){
        super(error[1].toString());
        errorResult = new ErrorResult();
        this.errorResult.setErrorCode(Integer.valueOf(error[0].toString()));
        this.errorResult.setErrorMessage(error[1].toString());
    }
    public ServiceException(String message,Object[] error){
        super(message);
        errorResult = new ErrorResult();
        this.errorResult.setErrorCode(Integer.valueOf(error[0].toString()));
        this.errorResult.setErrorMessage(message);
    }
}
