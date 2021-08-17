package com.franklions.example.domain.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: haoxubo
 * @Date: 2020/10/21 14:50
 */
@Mapper(componentModel = "spring")
public interface RoleUserConvert {
    RoleUserConvert INSTANCE= Mappers.getMapper(RoleUserConvert.class);
}
