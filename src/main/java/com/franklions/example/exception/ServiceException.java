package com.franklions.example.exception;

import lombok.Data;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
@Data
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
}
