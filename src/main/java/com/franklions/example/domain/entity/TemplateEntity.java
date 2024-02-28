package com.franklions.example.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.franklions.example.injector.annotation.UpdateField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@ApiModel(value = "模板信息")
@Data
@TableName("tb_template_info")
public class TemplateEntity extends BaseEntity {

    @ApiModelProperty(value = "记录ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "模板ID")
    @TableField("template_id")
    private String templateId;

    @ApiModelProperty(value = "名称")
    @UpdateField("name")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "状态")
    @UpdateField("status")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "类型")
    @UpdateField("dict_type")
    @TableField("dict_type")
    private String dictType;

    @ApiModelProperty(value = "版本",hidden = true)
    @Version
    private Integer version;
}
