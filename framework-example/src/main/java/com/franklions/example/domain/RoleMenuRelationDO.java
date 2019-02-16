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
@Table(name = "sys_relation")
public class RoleMenuRelationDO {
    @Id
    private Integer id;
    private Long menuid;
    private Integer roleid;

    
}
