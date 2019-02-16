package com.franklions.example.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-02-16
 * @since Jdk 1.8
 */
@Data
@Table(name = "sys_menu")
public class MenuDO {
    @Id
    private Long id;
    private String code;
    private String pcode;
    private String pcodes;
    private String name;
    private String icon;
    private String url;
    private Integer num;
    private Integer levels;
    private Integer ismenu;
    private String tips;
    private Integer status;
    private Integer isopen;

}
