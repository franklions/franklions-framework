package com.franklions.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.franklions.example.domain.PageParamRequest;
import com.franklions.example.domain.PageReturnValue;
import com.franklions.example.domain.dto.TemplateDTO;
import com.franklions.example.domain.entity.TemplateEntity;
import com.franklions.example.domain.request.TemplateRequest;

import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
public interface TemplateService extends IService<TemplateEntity> {

    boolean createTemplate(TemplateRequest request);

    Optional<TemplateEntity> getOneByName(String name);

    boolean editTemplate(TemplateEntity entity, TemplateRequest request);

    boolean removeTemplate(TemplateEntity entity);

    PageReturnValue<TemplateEntity> listTemplatePage(PageParamRequest request) throws JsonProcessingException;
}
