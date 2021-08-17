package com.franklions.example.domain.mapping;

import com.franklions.example.domain.dto.AccessRoleDTO;
import com.franklions.example.domain.dto.RoleUserDTO;
import com.franklions.example.domain.entity.AccessRoleEntity;
import com.franklions.example.domain.request.AccessRoleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: haoxubo
 * @Date: 2020/10/21 14:50
 */
@Mapper(componentModel = "spring")
public interface AccessRoleConvert {
    AccessRoleConvert INSTANCE= Mappers.getMapper(AccessRoleConvert.class);
    AccessRoleEntity req2entity(AccessRoleRequest request);
    AccessRoleDTO entity2dto(AccessRoleEntity entity);
    RoleUserDTO roleUser2dto(AccessRoleEntity entity);
}
