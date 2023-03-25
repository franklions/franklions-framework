package com.franklions.example.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/25
 * @since Jdk 1.8
 */
@Data
@TableName("tb_role_user")
public class RoleUser {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("app_id")
    private String appId;

    @TableField("role_id")
    private String roleId;

    @TableField("user_id")
    private String userId;
}
