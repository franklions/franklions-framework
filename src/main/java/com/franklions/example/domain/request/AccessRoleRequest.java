package com.franklions.example.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @Author: haoxubo
 * @Date: 2020/10/21 14:25
 */
@Data
public class AccessRoleRequest {

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Size(min = 1, max = 64, message = "应用名称不能超过64个字符")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 1, max = 128, message = "应用名称不能超过128个字符")
    private String roleName;

    /**
     * 是否超级管理员
     */
    @NotNull(message = "是否成为超级管理员不能为空")
    private Boolean isAdmin;

    /**
     * 描述
     */
    private String remark;
    /**
     * 禁用
     */
    private Boolean disabled;
}
