package com.franklions.example.service;

import com.franklions.example.domain.DeptDO;

import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-11
 * @since Jdk 1.8
 */
public interface IDeptService {
    Optional<DeptDO> getDeptInfoById(Integer id);
}
