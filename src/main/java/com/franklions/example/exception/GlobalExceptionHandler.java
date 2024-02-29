package com.franklions.example.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * @author Administrator
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(UserValidateException.class)
//    public ErrorResult handleUserValidateException(UserValidateException e) {
//        logger.error("用户验证错误", e);
//        return e.getErrorResult();
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(UserCenterServiceException.class)
//    public ErrorResult handleUserCenterException(UserCenterServiceException e) {
//        logger.error("IOT用户中心错误", e);
//        return e.getErrorResult();
//    }

//    /**
//     * 操作数据库出现异常:名称重复，外键关联
//     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ErrorResult handleException(DataIntegrityViolationException e) {
//        logger.error("操作数据库出现异常:", e);
//        return new ErrorResult(500,"操作数据库出现异常：字段重复、有外键关联等");
//    }
    /**
     * 404
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResult handleNoHandlerFoundException(NoHandlerFoundException e){
        logger.warn("非法的访问路径(404)", e);
        return new ErrorResult(400404,"非法的访问路径");
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.warn("缺少请求参数", e);
        return new ErrorResult(400000,"缺少请求参数");
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ErrorResult handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException e){
        logger.warn("缺少请求参数", e);
        return new ErrorResult(400000,"缺少请求参数");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("参数解析失败", e);
        return new ErrorResult(400000,"参数解析失败");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        logger.warn("参数类型异常",e);
        return new ErrorResult(400000,"参数类型异常");
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
                List<String> errors = result.getFieldErrors().stream().map(t->t.getDefaultMessage()).collect(Collectors.toList());
                return new ErrorResult(Integer.valueOf(ErrorCode.PARAMETER_VALID_ERROR[0].toString()),String.join(",",errors));
            }
        }

        return new ErrorResult(ErrorCode.PARAMETER_VALID_ERROR);
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
        String message = error.getDefaultMessage();

        return new ErrorResult(400001,message);
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
        List<String> errMessage = new ArrayList<>();
        violations.forEach((violation)->{
            String nodeName = "";
            Iterator<Path.Node> iterator = violation.getPropertyPath().iterator();
            while (iterator.hasNext()){
                nodeName = iterator.next().getName();
            }
            if(StringUtils.isNotBlank(violation.getMessage())) {
                errMessage.add(violation.getMessage());
            }
        });
        return new ErrorResult(400000, String.join(",",errMessage));
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResult handleValidationException(ValidationException e) {
        logger.warn("参数验证失败", e);
        return new ErrorResult(400000,"validation_exception");
    }

    /**
     * 404 not found resource
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundResourceException.class)
    public ErrorResult notFoundResourceException(NotFoundResourceException e){
        return e.getErrorResult();
    }

    /**
     * 请求参数错误
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ControllerValidationException.class)
    public ErrorResult controllerValidationException(ControllerValidationException e){
        return e.getErrorResult();
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("不支持当前请求方法", e);
        return new ErrorResult(400405,"不支持当前请求方法");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErrorResult handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.warn("不支持当前媒体类型", e);
        return new ErrorResult(400415,"不支持当前媒体类型");
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
        return new ErrorResult(500001,"内部服务器错误");
    }
}
