package com.franklions.example.domain.mapping;

import com.franklions.example.domain.dto.AccessPermissionDTO;
import com.franklions.example.domain.dto.RolePermissionDTO;
import com.franklions.example.domain.entity.AccessPermissionEntity;
import com.franklions.example.domain.request.AccessPermissionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: haoxubo
 * @Date: 2020/10/21 14:50
 */
@Mapper(componentModel = "spring")
public interface AccessPermissionConvert {
    AccessPermissionConvert INSTANCE= Mappers.getMapper(AccessPermissionConvert.class);
    AccessPermissionEntity req2entity(AccessPermissionRequest request);
    AccessPermissionDTO entity2dto(AccessPermissionEntity entity);
    RolePermissionDTO user2dto(AccessPermissionEntity entity);
}
