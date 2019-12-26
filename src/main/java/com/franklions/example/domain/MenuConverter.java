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
public interface MenuConverter {
    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    MenuDTO entity2dto(MenuDO menuDO);

    MenuDO dto2entity(MenuDTO m);
}
