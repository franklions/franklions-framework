package com.franklions.example.domain.dto;

import lombok.Data;

/**
 * @Author: haoxubo
 * @Date: 2020/10/22 10:42
 */
@Data
public class AccessPermissionDTO {
    /**
     * 记录ID 自增列记录ID
     */
    private Long id;

    /**
     * 应用ID
     */
    private String appId;

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
     * 描述
     */
    private String remark;

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
