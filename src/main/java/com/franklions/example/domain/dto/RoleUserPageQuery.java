package com.franklions.example.domain.dto;

import lombok.Data;

/**
 * @author flsh
 * @version 1.0
 * @date 2022/5/20
 * @since Jdk 1.8
 */
@Data
public class RoleUserPageQuery {
    private String roleId;
    private String userId;
}
