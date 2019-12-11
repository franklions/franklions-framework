package com.franklions.example.domain;

import lombok.*;

import javax.persistence.*;

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
    private Integer deptid;
    private String tips;
    private Integer version;
}
