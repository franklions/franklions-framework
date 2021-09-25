package com.franklions.example.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.franklions.example.exception.ErrorResult;
import lombok.Data;

/**
 * RPC框架返回结果
 * @author flsh
 * @version 1.0
 * @date 2021/2/3
 * @since Jdk 1.8
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T extends Object> {
    private boolean success;
    private Integer errorCode;
    private String errorMessage;
    private T data;
    private Long ts;

    public ResponseResult(boolean suc, Object[] errorCode, T data) {
        this.success=suc;
        this.errorCode = Integer.parseInt(errorCode[0].toString());
        this.errorMessage = errorCode[1].toString();
        this.data = data;
        this.ts = System.currentTimeMillis();
    }

    /**
     * 错误构造
     * @param errorResult
     */
    public ResponseResult(ErrorResult errorResult) {
        this.success=false;
        this.errorCode = errorResult.getErrorCode();
        this.errorMessage = errorResult.getErrorMessage();
        this.data=null;
        this.ts = System.currentTimeMillis();
    }
}
