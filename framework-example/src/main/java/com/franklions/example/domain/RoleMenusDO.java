package com.franklions.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 权限下的菜单
 * @author flsh
 * @version 1.0
 * @date 2019-02-16
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleMenusDO extends RoleDO {
    private List<MenuDO> listMenu;
}
