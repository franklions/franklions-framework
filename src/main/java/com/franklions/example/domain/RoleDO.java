package com.franklions.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/15
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_role")
public class RoleDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer num;
    private Integer pid;
    private String name;
    private String tips;
    private Integer version;

    @ManyToMany(targetEntity = MenuDO.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_relation", joinColumns = {@JoinColumn(name = "roleid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menuid", referencedColumnName = "id")})
    private Set<MenuDO> menus;

    @ManyToOne(targetEntity = DeptDO.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "deptid")
    @JsonIgnoreProperties({"users"})
    private DeptDO deptDO;
}
