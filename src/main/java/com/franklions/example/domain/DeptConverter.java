package com.franklions.example.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-12
 * @since Jdk 1.8
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptConverter INSTANCE = Mappers.getMapper(DeptConverter.class);

    DeptDTO entity2dto(DeptDO deptDO);

    @Mappings({
            @Mapping(target = "users",expression = "java( userDTO2DO(deptDTO.getUsers()))")
    })
    DeptDO  dto2entity(DeptDTO deptDTO);

    default Set<UserDO> userDTO2DO(Set<UserDTO> users) {
        Set<UserDO> collect = users.stream().map(m -> UserConverter.INSTANCE.dto2do(m)).collect(Collectors.toSet());
        return collect;
    }
}
