package com.franklions.example.domain.dto;

import lombok.Data;


/**
 * @Author: haoxubo
 * @Date: 2020/10/21 14:25
 */
@Data
public class AccessRoleDTO {

    /**
     * 记录ID 自增列记录ID
     */
    private Long id;

    /**
     * 应用ID
     */
    private String appId;

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

    /**
     * 创建时间
     */
    private Long gmtCreated;

    /**
     * 最后修改时间
     */
    private Long gmtModified;



}
