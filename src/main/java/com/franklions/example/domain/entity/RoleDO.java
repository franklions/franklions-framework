package com.franklions.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/15
 * @since Jdk 1.8
 */
@Data
@Table(name = "sys_role")
public class RoleDO {
    @Id
    private Integer id;
    private Integer num;
    private Integer pid;
    private String name;
    private Integer deptid;
    private String tips;
    private Integer version;
}
