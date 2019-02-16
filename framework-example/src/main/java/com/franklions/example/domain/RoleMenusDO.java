package com.franklions.example.domain;

import lombok.Data;

import java.util.List;

/**
 * 权限下的菜单
 * @author flsh
 * @version 1.0
 * @date 2019-02-16
 * @since Jdk 1.8
 */
@Data
public class RoleMenusDO extends RoleDO {
    private List<MenuDO> listMenu;
}
