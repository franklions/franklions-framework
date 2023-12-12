package com.franklions.example.service;

import com.franklions.example.domain.dto.LoginInfo;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
public interface AccessTokenService {
    LoginInfo getLoginInfo();
}
