package com.franklions.example.repository;

import com.franklions.example.domain.DeptDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-11
 * @since Jdk 1.8
 */
public interface DeptRepository extends JpaRepository<DeptDO,Integer> {
}
