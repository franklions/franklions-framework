package com.franklions.example.repository;

import com.franklions.example.domain.MenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-28
 * @since Jdk 1.8
 */
public interface MenuRepository extends JpaRepository<MenuDO,Integer>, JpaSpecificationExecutor<MenuDO> {
}
