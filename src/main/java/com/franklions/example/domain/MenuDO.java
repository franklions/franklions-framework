package com.franklions.example.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-02-16
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_menu")
public class MenuDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
