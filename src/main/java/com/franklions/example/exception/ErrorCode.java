package com.franklions.example.exception;

public class ErrorCode {

    //系统通用类
    public static Object[] PARAMETER_VALID_ERROR = {400001, "参数验证失败", "PARAMETER_VALID_ERROR"};
    public static Object[] NO_FOUND_RECORD_ERROR={400002,"未找到相关记录","NO_FOUND_RECORD"};
    public static Object[] EXIST_RECORD_ERROR={400003,"相关记录已经存在","EXIST_RECORD_ERROR"};
    public static Object[] USER_UNAUTHORIZED={401004,"用户未授权","USER_UNAUTHORIZED"};
    public static Object[] USER_TOKEN_EXPIRE={401005,"用户TOKEN过期","USER_TOKEN_EXPIRE"};
}
