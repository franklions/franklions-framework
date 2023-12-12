package com.franklions.example.service.impl;

import com.franklions.example.domain.dto.LoginInfo;
import com.franklions.example.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private HttpServletRequest request;

    @Override
    public LoginInfo getLoginInfo() {
        String uid = request.getHeader("x-saas-uid");
        LoginInfo info = new LoginInfo();
        info.setUserId(10L);
        info.setDataScope(Arrays.asList(1L,2L));
        return info;
    }
}
