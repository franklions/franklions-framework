package com.franklions.example.service;

import com.franklions.example.domain.RoleDTO;
import com.franklions.example.domain.RoleMenuDTO;

import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-12
 * @since Jdk 1.8
 */
public interface IRoleService {
    Optional<RoleDTO> getRoleInfoById(Integer id);

    Optional<RoleMenuDTO> getRoleMenusById(Integer id);
}
