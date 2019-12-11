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
@Table(name = "sys_relation")
public class RoleMenuRelationDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long menuid;
    private Integer roleid;

    
}
