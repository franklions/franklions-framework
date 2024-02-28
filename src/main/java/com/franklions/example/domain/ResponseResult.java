package com.franklions.example.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.franklions.example.exception.ErrorResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RPC框架返回结果
 * @author flsh
 * @version 1.0
 * @date 2021/2/3
 * @since Jdk 1.8
 */
@ApiModel(value = "接口返回结果")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T extends Object> {

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "错误码")
    private Integer errorCode;

    @ApiModelProperty(value = "错误消息")
    private String errorMessage;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "服务器返回时间戳")
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
