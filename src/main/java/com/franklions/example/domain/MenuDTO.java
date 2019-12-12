package com.franklions.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-12
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class MenuDTO {
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
