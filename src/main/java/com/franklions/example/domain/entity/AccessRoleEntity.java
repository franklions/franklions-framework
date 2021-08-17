package com.franklions.example.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_access_role")
public class AccessRoleEntity extends BaseEntity {
    /**
     * 记录ID 自增列记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 应用ID
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 角色编码
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 是否超级管理员
     */
    @Column(name = "is_admin")
    private Boolean isAdmin;

}