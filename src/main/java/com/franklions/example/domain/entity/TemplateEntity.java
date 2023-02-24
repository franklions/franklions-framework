package com.franklions.example.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@Data
@TableName("tb_template_info")
public class TemplateEntity extends BaseEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("template_id")
    private String templateId;

    @TableField("name")
    private String name;

    @TableField("status")
    private Integer status;

    @TableField("dict_type")
    private String dictType;

    @Version
    private Integer version;
}
