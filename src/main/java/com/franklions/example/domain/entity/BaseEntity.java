package com.franklions.example.domain.entity;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
@Data
public class BaseEntity {

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
    @Column(name = "gmt_created")
    private Long gmtCreated;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Long gmtModified;

    /**
     * 删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private Long ts;

    public void setCreateData(){
        this.disabled=false;
        this.deleted=false;
        this.gmtCreated= System.currentTimeMillis();
        this.gmtModified=System.currentTimeMillis();
        this.ts = System.currentTimeMillis();
    }
}
