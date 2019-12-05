package com.franklions.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局错误处理
 * @author flsh
 * @version 1.0
 * @date 2019-06-30
 * @since Jdk 1.8
 */
@ControllerAdvice
@RestControllerAdvice
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

//    @ExceptionHandler({IllegalArgumentException.class})
//    public ResponseResult<Object> illegalArgumentException(IllegalArgumentException e) {
//        logger.warn("Illegal Argument Exception", e);
//        return responseUtil.failedResult(ErrorCode.dataLost);
//    }
//
//    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
//    public ResponseResult<Object> requestHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
//        logger.warn("Unsupported Media Type", e);
//        return responseUtil.failedResult(ErrorCode.dataLost);
//    }
//
//    @ExceptionHandler({HttpMessageNotReadableException.class})
//    public ResponseResult<Object> requestHttpMessageNotReadableException(HttpMessageNotReadableException e) {
//        logger.warn("HttpMessageNotReadableException", e);
//        return responseUtil.failedResult(ErrorCode.dataLost);
//    }
//
//    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
//    public ResponseResult<Object> requestHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//        logger.warn("Method Not Allowed", e);
//        return responseUtil.failedResult(ErrorCode.dataLost);
//    }
//
//
//    @ExceptionHandler({TypeMismatchException.class})
//    public ResponseResult<Object> requestTypeMismatch(TypeMismatchException e) {
//        logger.warn("TypeMismatchException", e);
//        return responseUtil.failedResult(ErrorCode.dataLost);
//    }
//
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseResult<Object> requestMethodArgumentNotValid(MethodArgumentNotValidException e) {
//        logger.warn("MethodArgumentNotValidException", e);
//        return responseUtil.failedResult(ErrorCode.dataLost);
//    }
//
//    @ExceptionHandler({Exception.class})
//    public ResponseResult<Object> handleAllError(HttpServletRequest request, Throwable ex) {
//        logger.error(ex.getMessage(), ex);
//        return responseUtil.failedResult(ErrorCode.serverError);
//    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleAllError(HttpServletRequest request, Throwable ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.notFound().build();
    }

}
