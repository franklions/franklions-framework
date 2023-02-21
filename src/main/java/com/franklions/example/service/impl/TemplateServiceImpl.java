package com.franklions.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.domain.PageParamRequest;
import com.franklions.example.domain.PageReturnValue;
import com.franklions.example.domain.entity.TemplateEntity;
import com.franklions.example.domain.mapping.TemplateConverter;
import com.franklions.example.domain.request.TemplateRequest;
import com.franklions.example.mapper.TemplateMapper;
import com.franklions.example.service.TemplateService;
import com.franklions.example.utils.BeanUtils;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, TemplateEntity> implements TemplateService {

    @Autowired
    private TemplateConverter converter;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean createTemplate(TemplateRequest request) {
        TemplateEntity entity = converter.req2entity(request);
        entity.initDefaultValue();
        return save(entity);
    }

    @Override
    public Optional<TemplateEntity> getOneByName(String name) {
        TemplateEntity entity = lambdaQuery().eq(TemplateEntity::getName, name).one();
        return Optional.ofNullable(entity);
    }

    @Override
    public boolean editTemplate(TemplateEntity entity, TemplateRequest request) {
        TemplateEntity source = converter.req2entity(request);
        BeanUtils.mergeObject(entity,source);
        entity.setGmtModified(System.currentTimeMillis());
        return updateById(entity);
    }

    @Override
    public boolean removeTemplate(TemplateEntity entity) {
        return removeById(entity);
    }

    @Override
    public PageReturnValue<TemplateEntity> listTemplatePage(PageParamRequest request) throws JsonProcessingException {
        IPage<TemplateEntity> page = new Page<>(request.getPage(),request.getSize());
        QueryWrapper<TemplateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true,request.getDesc(), CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,request.getSort()));

        LambdaQueryWrapper<TemplateEntity> lambdaQueryWrapper = queryWrapper.lambda();
        lambdaQueryWrapper.eq(TemplateEntity::getDeleted,false);

        TemplateEntity entity = new TemplateEntity();
        if (request.getQueryStr()!= null && !"".equals(request.getQueryStr())) {
            entity = objectMapper.readValue(request.getQueryStr(), TemplateEntity.class);
        }

        lambdaQueryWrapper.like(StringUtils.isNotBlank(entity.getName()),TemplateEntity::getName,entity.getName())
                .eq(StringUtils.isNotBlank(entity.getDictType()),TemplateEntity::getDictType,entity.getDictType())
                .eq(entity.getStatus()!=null,TemplateEntity::getStatus,entity.getStatus());


        IPage<TemplateEntity> data = getBaseMapper().selectPage(page,queryWrapper);
        PageReturnValue<TemplateEntity> retval = new PageReturnValue<>(data);
        return retval;
    }
}
