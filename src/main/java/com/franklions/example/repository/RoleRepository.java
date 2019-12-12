package com.franklions.example.repository;

import com.franklions.example.domain.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-12
 * @since Jdk 1.8
 */
public interface RoleRepository extends JpaRepository<RoleDO,Integer> {
}
