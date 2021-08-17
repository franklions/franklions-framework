package com.franklions.example.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_role_user")
public class RoleUserEntity {
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
     * 用户ID(企业员工uid)
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 创建时间
     */
    @Column(name = "gmt_created")
    private Long gmtCreated;

    /**
     * 时间戳
     */
    private Long ts;
}