package com.franklions.example.domain.dto;

import lombok.Data;

/**
 * @Author: haoxubo
 * @Date: 2020/10/22 15:40
 */
@Data
public class RoleUserDTO {

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否超级管理员
     */
    private Boolean isAdmin;

    /**
     * 禁用
     */
    private Boolean disabled;


}
