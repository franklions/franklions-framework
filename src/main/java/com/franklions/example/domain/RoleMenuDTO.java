package com.franklions.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

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
public class RoleMenuDTO {
    private Integer id;
    private Integer num;
    private Integer pid;
    private String name;
    private String tips;
    private Integer version;

    private Set<MenuDTO> menus;

    private DeptDTO deptDTO;
}
