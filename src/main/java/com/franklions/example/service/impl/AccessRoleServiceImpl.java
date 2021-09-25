package com.franklions.example.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.AccessRoleDTO;
import com.franklions.example.domain.PageParamRequest;
import com.franklions.example.domain.PageReturnValue;
import com.franklions.example.domain.entity.AccessRoleEntity;
import com.franklions.example.domain.mapping.AccessRoleConvert;
import com.franklions.example.domain.request.AccessRoleRequest;
import com.franklions.example.mapper.AccessRoleMapper;
import com.franklions.example.service.AccessRoleService;
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
 * @Date: 2020/10/21 14:28
 */
@Service
public class AccessRoleServiceImpl implements AccessRoleService {

    private AccessRoleMapper mapper;
    private ObjectMapper objectMapper;
    private AccessRoleConvert convert;


    @Autowired
    public AccessRoleServiceImpl(AccessRoleMapper mapper,
                                 ObjectMapper objectMapper,
                                 AccessRoleConvert convert) {
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.convert = convert;
    }

    @Override
    public void createRole(String appId, AccessRoleRequest request) {
        AccessRoleEntity accessRoleEntity = convert.INSTANCE.req2entity(request);
        accessRoleEntity.setRoleId(IdGenerator.newUUID());
        accessRoleEntity.setAppId(appId);
        accessRoleEntity.initDefaultValue();
        mapper.insert(accessRoleEntity);
    }

    @Override
    public Optional<AccessRoleDTO> roleOne(String appId, String roleId) {
        AccessRoleEntity entity = mapper.selectOne(new AccessRoleEntity() {{
            setAppId(appId);
            setRoleId(roleId);
            setDeleted(false);
        }});
        AccessRoleDTO accessRoleDTO = convert.entity2dto(entity);
        return Optional.ofNullable(accessRoleDTO);
    }

    @Override
    public List<AccessRoleDTO> RoleList(String appId) {
        List<AccessRoleEntity> list = mapper.select(new AccessRoleEntity() {{
            setAppId(appId);
            setDeleted(false);
        }});
        List<AccessRoleDTO> retList = list.stream().map(a ->convert.entity2dto(a))
                .collect(Collectors.toList());
        return retList;
    }

    @Override
    public PageReturnValue<AccessRoleDTO> listAccessRolePage(String appId, PageParamRequest request) throws IOException {
        AccessRoleEntity entity = new AccessRoleEntity();
        if (request.getQueryStr()!= null && !"".equals(request.getQueryStr())) {
            entity = objectMapper.readValue(request.getQueryStr(), AccessRoleEntity.class);
        }
        Example example = new Example(AccessRoleEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.DELETED,false)
        .andEqualTo(Constants.APPID,appId);
        //创建查询条件

        if (StringUtils.isNotBlank(entity.getRoleName())) {
            criteria.andLike(Constants.ROLENAME, "%"+entity.getRoleName()+"%");
        }
        //排序方式
        if(request.getDesc()){
            example.orderBy(request.getSort()).desc();
        }else{
            example.orderBy(request.getSort()).asc();
        }
        //分页
        RowBounds rowBounds = new RowBounds(request.getStart(), request.getCount());

        List<AccessRoleEntity> listRole =mapper.selectByExampleAndRowBounds(example, rowBounds);

        if (listRole == null || listRole.size() < 1) {
            return PageReturnValue.empty();
        }
        List<AccessRoleDTO> retList = listRole.stream().map(a->convert.entity2dto(a))
                .collect(Collectors.toList());
        //获取总数
        Integer total = mapper.selectCountByExample(example);

        PageReturnValue<AccessRoleDTO> returnValue = new PageReturnValue<AccessRoleDTO>(retList, total);

        return returnValue;

    }

    @Override
    public void editRole(AccessRoleEntity entity, AccessRoleRequest request) {
        AccessRoleEntity accessRoleEntity = convert.req2entity(request);
        BeanUtils.mergeObject(entity,accessRoleEntity);
        entity.setGmtModified(System.currentTimeMillis());
        mapper.updateByPrimaryKey(entity);
    }

    @Override
    public Optional<AccessRoleEntity> getRoleByAppIdAndRoleId(String appId, String roleId) {
        AccessRoleEntity entity = mapper.selectOne(new AccessRoleEntity() {{
            setAppId(appId);
            setRoleId(roleId);
            setDeleted(false);
        }});
        return Optional.ofNullable(entity);
    }

    @Override
    public void removeRole(AccessRoleEntity entity) {
        entity.setDeleted(true);
        entity.setGmtModified(System.currentTimeMillis());
        mapper.updateByPrimaryKey(entity);
    }
}
