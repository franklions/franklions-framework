package com.franklions.example.controller;

import com.franklions.example.domain.ResponseResult;
import com.franklions.example.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/20
 * @since Jdk 1.8
 */
public class BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public ResponseResult success(){
        return new ResponseResult<>(true, ErrorCode.SUCCESS,null);
    }

    public <T> ResponseResult<T> success(T data){
        return new ResponseResult<T>(true,ErrorCode.SUCCESS,data);
    }

    public ResponseResult fail(Object[] errorCode){
        return new ResponseResult(false,errorCode,null);
    }
}
