package com.franklions.example.repository;

import com.franklions.example.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
public interface UserRepository extends JpaRepository<UserDO,Integer> {
    List<UserDO> findByNameLike(String name);

    UserDO findByAccount(String account);
}
