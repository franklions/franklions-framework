package com.franklions.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 全局异常处理
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.warn("缺少请求参数", e);
        return new ErrorResult(400,"required_parameter_is_not_present");
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ErrorResult handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException e){
        logger.warn("缺少请求参数", e);
        return new ErrorResult(400,"required_parameter_is_not_present");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("参数解析失败", e);
        return new ErrorResult(400,"could_not_read_json");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        logger.warn("参数类型异常",e);
        return new ErrorResult(400,"required_parameter_type_mismatch");
    }

    /**
     * 400 - Bad Request
     * @Valid 抛出的异常是 MethodArgumentNotValidException BindingResult 统一处理方法
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        if(result.hasErrors()){
            if(result.getFieldErrors() != null && result.getFieldErrors().size() > 0){
                StringBuilder sbErr = new StringBuilder();
                for(FieldError error : result.getFieldErrors()){
                    sbErr.append(String.format("%s:%s", error.getField(), error.getDefaultMessage()));
                }

                return new ErrorResult(400,sbErr.toString());
            }
        }

        return new ErrorResult(400,"required_parameter_notvalid");
    }

    /**
     * 400 - Bad Request
     * BindingResult 统一处理方法
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResult handleBindException(BindException e) {
        logger.warn("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new ErrorResult(400,message);
    }

    /**
     * @Validated 验证抛出的异常是javax.validation.ConstraintViolationException
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResult handleServiceException(ConstraintViolationException e) {
        logger.warn("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new ErrorResult(400,"parameter:" + message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResult handleValidationException(ValidationException e) {
        logger.warn("参数验证失败", e);
        return new ErrorResult(400,"validation_exception");
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("不支持当前请求方法", e);
        return new ErrorResult(405,"request_method_not_supported");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErrorResult handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.warn("不支持当前媒体类型", e);
        return new ErrorResult(415,"content_type_not_supported");
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public ErrorResult handleServiceException(ServiceException e) {
        logger.error("业务逻辑异常", e);
        return e.getErrorResult();
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult handleException(Exception e) {
        logger.error("未知异常", e);
        return new ErrorResult(500,"未知异常：" + e.getMessage());
    }

//    /**
//     * 操作数据库出现异常:名称重复，外键关联
//     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ErrorResult handleException(DataIntegrityViolationException e) {
//        logger.error("操作数据库出现异常:", e);
//        return new ErrorResult(500,"操作数据库出现异常：字段重复、有外键关联等");
//    }


}
