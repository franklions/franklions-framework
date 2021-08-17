package com.franklions.example.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * @Author: haoxubo
 * @Date: 2020/10/22 10:36
 */
@Data
public class AccessPermissionRequest {

    /**
     * 资源编码，用于与菜单关联
     */
    @NotBlank(message = "权限编码不能为空")
    @Size(min = 1, max = 64, message = "权限编码不能超过64个字符")
    private String permissionCode;

    /**
     * 资源名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Size(min = 1, max = 128, message = "权限名称不能超过128个字符")
    private String permissionName;

    /**
     * 描述
     */
    private String remark;
    /**
     * 禁用
     */
    private Boolean disabled;

}
