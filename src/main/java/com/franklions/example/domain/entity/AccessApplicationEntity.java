package com.franklions.example.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_access_application")
public class AccessApplicationEntity extends BaseEntity{
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
     * 应用名称
     */
    @Column(name = "app_name")
    private String appName;


}