package com.franklions.example.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.franklions.example.injector.annotation.UpdateField;
import lombok.Data;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@Data
@TableName("tb_access_application")
public class AccessApplication extends BaseEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("app_id")
    private String appId;

    @UpdateField("app_name")
    @TableField("app_name")
    private String appName;
}
