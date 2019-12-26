package com.franklions.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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

    /**
     * 放弃对中间表的维护权，解决保存中主键冲突的问题,关系表由Role维护
     */
    @ManyToMany(mappedBy = "menus",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"menus"})
    private Set<RoleDO> roles;
}
