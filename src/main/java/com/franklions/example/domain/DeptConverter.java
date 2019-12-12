package com.franklions.example.domain;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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

    DeptDO  dto2entity(DeptDTO deptDTO);
}
