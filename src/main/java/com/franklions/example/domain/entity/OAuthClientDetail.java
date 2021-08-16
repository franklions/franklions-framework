package com.franklions.example.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "oauth_client_detail")
public class OAuthClientDetail extends BaseEntity {
    /**
     * 记录ID 自增列记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 客户端ID
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 客户端访问密码
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * 客户端访问资源类型
     */
    @Column(name = "resource_id")
    private String resourceId;

}