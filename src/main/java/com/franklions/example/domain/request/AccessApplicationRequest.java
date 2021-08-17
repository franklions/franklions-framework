package com.franklions.example.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
@Data
public class AccessApplicationRequest {
    @NotBlank(message = "应用名称不能为空")
    @Size(min = 1, max = 200, message = "应用名称不能超过200个字符")
    private String appName;
    @Size(min = 0, max = 200, message = "用户名不能超过200个字符")
    private String remark;
}
