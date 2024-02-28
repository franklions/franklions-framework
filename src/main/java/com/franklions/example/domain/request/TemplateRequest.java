package com.franklions.example.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@ApiModel(value = "添加模板请求")
@Data
public class TemplateRequest {

    @ApiModelProperty(value = "模板ID")
    private String templateId;

    @ApiModelProperty(value = "模板名称",required = true)
    @NotBlank(message = "名称不能为空")
    @Size(min = 1, max = 200, message = "名称不能超过200个字符")
    private String name;

    /**
     * 是否超级管理员
     */
    @ApiModelProperty(value = "是否管理",required = true)
    @NotNull(message = "是否成为超级管理员不能为空")
    private Boolean isAdmin;

    @ApiModelProperty(value = "状态",required = true)
    @NotBlank(message = "状态不能为空")
    @Pattern(regexp = "^(0|1|2|3|-1)$",message = "状态值不正确")
    private String status;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型",required = true)
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）")
    private String dictType;
}
