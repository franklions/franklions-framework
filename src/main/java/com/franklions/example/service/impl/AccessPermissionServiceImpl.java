package com.franklions.example.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.AccessPermissionDTO;
import com.franklions.example.domain.PageParamRequest;
import com.franklions.example.domain.PageReturnValue;
import com.franklions.example.domain.entity.AccessPermissionEntity;
import com.franklions.example.domain.mapping.AccessPermissionConvert;
import com.franklions.example.domain.request.AccessPermissionRequest;
import com.franklions.example.mapper.AccessPermissionMapper;
import com.franklions.example.service.AccessPermissionService;
import com.franklions.example.utils.BeanUtils;
import com.franklions.example.utils.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: haoxubo
 * @Date: 2020/10/22 10:31
 */
@Service
public class AccessPermissionServiceImpl implements AccessPermissionService {

    private AccessPermissionMapper mapper;
    private ObjectMapper objectMapper;
    private AccessPermissionConvert convert;

    @Autowired
    public AccessPermissionServiceImpl(AccessPermissionMapper mapper,
                                 ObjectMapper objectMapper,
                                       AccessPermissionConvert convert) {
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.convert = convert;
    }

    @Override
    public void createPermission(String appId, AccessPermissionRequest request) {
        AccessPermissionEntity entity = convert.req2entity(request);
        entity.setPermissionId(IdGenerator.newUUID());
        entity.setAppId(appId);
        entity.initDefaultValue();
        mapper.insert(entity);
    }

    @Override
    public Optional<AccessPermissionEntity> getPerByAppIdAndPerId(String appId, String permissionId) {
        AccessPermissionEntity entity = mapper.selectOne(new AccessPermissionEntity() {{
            setAppId(appId);
            setPermissionId(permissionId);
            setDeleted(false);
        }});
        return Optional.ofNullable(entity);
    }

    @Override
    public void editPermission(AccessPermissionEntity entity, AccessPermissionRequest request) {
        AccessPermissionEntity permissionEntity = convert.req2entity(request);
        BeanUtils.mergeObject(entity,permissionEntity);
        entity.setGmtModified(System.currentTimeMillis());
        mapper.updateByPrimaryKey(entity);
    }

    @Override
    public void removePermission(AccessPermissionEntity entity) {
        entity.setDeleted(true);
        entity.setGmtCreated(System.currentTimeMillis());
        mapper.updateByPrimaryKey(entity);
    }

    @Override
    public Optional<AccessPermissionDTO> permissionOne(String appId, String permissionId) {
        AccessPermissionEntity entity = mapper.selectOne(new AccessPermissionEntity() {{
            setAppId(appId);
            setPermissionId(permissionId);
            setDeleted(false);
        }});
        AccessPermissionDTO permissionDTO = convert.entity2dto(entity);
        return Optional.ofNullable(permissionDTO);
    }

    @Override
    public List<AccessPermissionDTO> permissionList(String appId) {
        List<AccessPermissionEntity> list = mapper.select(new AccessPermissionEntity() {{
            setAppId(appId);
            setDeleted(false);
        }});
        List<AccessPermissionDTO> retList = list.stream().map(a ->convert.entity2dto(a))
                .collect(Collectors.toList());
        return retList;
    }

    @Override
    public PageReturnValue<AccessPermissionDTO> listPermissionPage(String appId, PageParamRequest request) throws IOException {
        AccessPermissionEntity entity = new AccessPermissionEntity();
        if (request.getQueryStr()!= null && !"".equals(request.getQueryStr())) {
            entity = objectMapper.readValue(request.getQueryStr(), AccessPermissionEntity.class);
        }
        Example example = new Example(AccessPermissionEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.DELETED,false)
                .andEqualTo(Constants.APPID,appId);
        //创建查询条件

        if (StringUtils.isNotBlank(entity.getPermissionName())) {
            criteria.andLike(Constants.ROLENAME, "%"+entity.getPermissionName()+"%");
        }
        //排序方式
        if(request.getDesc()){
            example.orderBy(request.getSort()).desc();
        }else{
            example.orderBy(request.getSort()).asc();
        }
        //分页
        RowBounds rowBounds = new RowBounds(request.getStart(), request.getCount());

        List<AccessPermissionEntity> listPermission =mapper.selectByExampleAndRowBounds(example, rowBounds);

        if (listPermission == null || listPermission.size() < 1) {
            return PageReturnValue.empty();
        }
        List<AccessPermissionDTO> retList = listPermission.stream().map(a->convert.entity2dto(a))
                .collect(Collectors.toList());
        //获取总数
        Integer total = mapper.selectCountByExample(example);

        PageReturnValue<AccessPermissionDTO> returnValue = new PageReturnValue<AccessPermissionDTO>(retList, total);

        return returnValue;
    }


}
