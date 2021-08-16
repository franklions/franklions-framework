package com.franklions.example.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_device_model_detail_aligenie")
public class DeviceModelDetailAligenie extends BaseEntity {
    /**
     * 记录ID 自增列记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 设备型号ID
     */
    @Column(name = "model_id")
    private String modelId;

    /**
     * 设备名称
     */
    @Column(name = "device_name")
    private String deviceName;

    /**
     * 品牌
     */
    private String band;

    /**
     * 天猫精灵设备类型
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 设备型号名称
     */
    @Column(name = "model_code")
    private String modelCode;

    /**
     * 设备显示图标
     */
    @Column(name = "icon_url")
    private String iconUrl;

    /**
     * 产品支持的操作
     */
    private String actions;

    /**
     * 设备支持的属性状态
     */
    private String properties;

    /**
     * 产品扩展属性
     */
    private String extensions;


}