package com.franklions.example.service;

import com.franklions.example.domain.dto.AccessApplicationDTO;
import com.franklions.example.domain.dto.PageParamRequest;
import com.franklions.example.domain.dto.PageReturnValue;
import com.franklions.example.domain.entity.AccessApplicationEntity;
import com.franklions.example.domain.request.AccessApplicationRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/20
 * @since Jdk 1.8
 */
public interface AccessApplicationService {
    List<AccessApplicationDTO> listAll();

    Optional<AccessApplicationDTO> getAppByAppId(String appId);

    PageReturnValue<AccessApplicationDTO> listAccessApplicationPage(PageParamRequest request) throws IOException;

    Optional<AccessApplicationEntity> getAppByName(String appName);

    void createApplication(AccessApplicationRequest request);

    void editApplication(AccessApplicationEntity entity, AccessApplicationRequest request);

    void removeApplication(AccessApplicationEntity entity);

    Optional<AccessApplicationEntity> getVerifyByAppId(String appId);
}
