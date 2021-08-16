package com.franklions.example.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "oauth_access_token")
public class OAuthAccessToken {
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
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户token
     */
    private String token;

    /**
     * 过期时间
     */
    @Column(name = "expired_time")
    private Integer expiredTime;

    /**
     * 刷新token
     */
    @Column(name = "refresh_token")
    private String refreshToken;

    /**
     * 刷新token过期时间
     */
    @Column(name = "refresh_expired_time")
    private Integer refreshExpiredTime;

    /**
     * 创建时间
     */
    @Column(name = "gmt_created")
    private Long gmtCreated;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Long gmtModified;

    /**
     * 时间戳
     */
    private Long ts;
}