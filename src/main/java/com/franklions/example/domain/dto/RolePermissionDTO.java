package com.franklions.example.domain.dto;

import lombok.Data;

/**用户权限
 * @Author: haoxubo
 * @Date: 2020/10/23 16:29
 */
@Data
public class RolePermissionDTO {
    /**
     * 资源ID，用于关联关系
     */
    private String permissionId;

    /**
     * 资源编码，用于与菜单关联
     */
    private String permissionCode;

    /**
     * 资源名称
     */
    private String permissionName;

    /**
     * 资源类型
     */
    private String permissionType;

    /**
     * 禁用
     */
    private Boolean disabled;


}
