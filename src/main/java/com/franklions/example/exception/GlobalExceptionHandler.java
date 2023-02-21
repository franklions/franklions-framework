package com.franklions.example.exception;

import com.franklions.example.domain.ResponseResult;
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
import javax.validation.ValidationException;
import java.util.Set;

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
 * RPC框架返回结果
 * 当使用RPC返回结果时开启下面的注解 上面跟下面只保留一个即可
 */
@ResponseStatus(HttpStatus.OK)
@ExceptionHandler(NoHandlerFoundException.class)
public ResponseResult handleNoHandlerFoundException(NoHandlerFoundException e){
    logger.warn("非法的访问路径(404)", e);
    return new ResponseResult(new ErrorResult(400404,"非法的访问路径"));
}


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.warn("缺少请求参数", e);
        return new ResponseResult(new ErrorResult(400000,"缺少请求参数"));
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseResult handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException e){
        logger.warn("缺少请求参数", e);
        return new ResponseResult(new ErrorResult(400000,"缺少请求参数"));
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("参数解析失败", e);
        return new ResponseResult(new ErrorResult(400000,"参数解析失败"));
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        logger.warn("参数类型异常",e);
        return new ResponseResult(new ErrorResult(400000,"参数类型异常"));
    }

    /**
     * 400 - Bad Request
     * @Valid 抛出的异常是 MethodArgumentNotValidException BindingResult 统一处理方法
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        if(result.hasErrors()){
            if(result.getFieldErrors() != null && result.getFieldErrors().size() > 0){
                StringBuilder sbErr = new StringBuilder();
                for(FieldError error : result.getFieldErrors()){
                    sbErr.append(String.format("%s:%s", error.getField(), error.getDefaultMessage()));
                }
                return new ResponseResult(new ErrorResult(Integer.valueOf(ErrorCode.PARAMETER_VALID_ERROR[0].toString()),sbErr.toString()));
            }
        }

        return new ResponseResult(new ErrorResult(ErrorCode.PARAMETER_VALID_ERROR));
    }

    /**
     * 400 - Bad Request
     * BindingResult 统一处理方法
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public ResponseResult handleBindException(BindException e) {
        logger.warn("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new ResponseResult(new ErrorResult(400001,message));
    }

    /**
     * @Validated 验证抛出的异常是javax.validation.ConstraintViolationException
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult handleServiceException(ConstraintViolationException e) {
        logger.warn("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new ResponseResult(new ErrorResult(400000,"parameter:" + message));
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public ResponseResult handleValidationException(ValidationException e) {
        logger.warn("参数验证失败", e);
        return new ResponseResult(new ErrorResult(400000,"validation_exception"));
    }

    /**
     * 404 not found resource
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseResult notFoundResourceException(NotFoundResourceException e){
        return new ResponseResult(e.getErrorResult());
    }

    /**
     * 请求参数错误
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ControllerValidationException.class)
    public ResponseResult controllerValidationException(ControllerValidationException e){
        return new ResponseResult(e.getErrorResult());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("不支持当前请求方法", e);
        return new ResponseResult(new ErrorResult(400405,"不支持当前请求方法"));
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseResult handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.warn("不支持当前媒体类型", e);
        return new ResponseResult(new ErrorResult(400415,"不支持当前媒体类型"));
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServiceException.class)
    public ResponseResult handleServiceException(ServiceException e) {
        logger.error("业务逻辑异常", e);
        return new ResponseResult(e.getErrorResult());
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        logger.error("未知异常", e);
        return new ResponseResult(new ErrorResult(500001,"内部服务器错误"));
    }
}
