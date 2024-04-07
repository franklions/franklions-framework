package com.franklions.example.exception;

/**
 * 未能通过的权限认证异常
 * @author flsh
 * @version 1.0
 * @date 2024/4/2
 * @since Jdk 1.8
 */
public class NotPermissionException extends RuntimeException{
    public NotPermissionException(String permission) {
        super(permission);
    }
}
