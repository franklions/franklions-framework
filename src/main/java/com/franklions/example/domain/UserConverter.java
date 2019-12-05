package com.franklions.example.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 实体映射方式，有两种方式一种通过调用INSTANCE 实例变量。一种注入spring bean
 * @author flsh
 * @version 1.0
 * @date 2019-12-05
 * @since Jdk 1.8
 */
@Mapper(componentModel = "spring")
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mappings({
            @Mapping(target = "deptName",source = "deptDO.simplename")
    })
    UserDTO domain2dto(UserDO user);
}
