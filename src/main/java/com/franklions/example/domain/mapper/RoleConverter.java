package com.franklions.example.domain.mapper;

import com.franklions.example.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-12
 * @since Jdk 1.8
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    RoleDTO entity2dto(RoleDO roleDO);

    @Mappings({
            @Mapping(target = "deptDTO",expression = "java( DeptConverter.INSTANCE.entity2dto(roleDO.getDeptDO()))"),
            @Mapping(target = "menus",expression = "java( menuDO2MenuDTO(roleDO.getMenus()))"),
    })
    RoleMenuDTO entity2RoleMenuDTO(RoleDO roleDO);

    default Set<MenuDTO> menuDO2MenuDTO(Set<MenuDO> menuDOs) {
        System.out.println("调用menuDO2MenuDTO");
        Set<MenuDTO> collect = menuDOs.stream().map(m -> MenuConverter.INSTANCE.entity2dto(m)).collect(Collectors.toSet());
        return collect;
    }

    @Mappings({
            @Mapping(target = "menus",expression = "java( menuDTO2menuDO(roleDTO.getMenus()))"),
    })
    RoleDO dto2entity(RoleDTO roleDTO);

    default Set<MenuDO> menuDTO2menuDO(Set<MenuDTO> menus) {
        Set<MenuDO> collect = menus.stream().map(m -> MenuConverter.INSTANCE.dto2entity(m)).collect(Collectors.toSet());
        return collect;
    }
}
