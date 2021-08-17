package com.franklions.example.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_access_permission")
public class AccessPermissionEntity extends BaseEntity{
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
     * 资源ID，用于关联关系
     */
    @Column(name = "permission_id")
    private String permissionId;

    /**
     * 资源编码，用于与菜单关联
     */
    @Column(name = "permission_code")
    private String permissionCode;

    /**
     * 资源名称
     */
    @Column(name = "permission_name")
    private String permissionName;

    /**
     * 资源类型
     */
    @Column(name = "permission_type")
    private String permissionType;

}