package com.franklions.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.ValidationException;


/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ControllerValidationException extends ValidationException {
    private ErrorResult errorResult;

    public ControllerValidationException(String message, ErrorResult errorResult) {
        super(message);
        this.errorResult = errorResult;
    }

    public ControllerValidationException(String message, String errorCode, ErrorResult errorResult) {
        super(message);
        this.errorResult = errorResult;
    }

    public ControllerValidationException(Throwable exception, ErrorResult errorResult) {
        super(exception);
        this.errorResult = errorResult;
    }

    public ControllerValidationException(Object[] error){
        super(error[1].toString());
        errorResult = new ErrorResult();
        this.errorResult.setErrorCode(Integer.valueOf(error[0].toString()));
        this.errorResult.setErrorMessage(error[1].toString());
    }
    public ControllerValidationException(String message,Object[] error){
        super(message);
        errorResult = new ErrorResult();
        this.errorResult.setErrorCode(Integer.valueOf(error[0].toString()));
        this.errorResult.setErrorMessage(message);
    }
}
