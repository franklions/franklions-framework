package com.franklions.example.mapper;

import com.franklions.example.domain.entity.RoleUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通用 mapper
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@Mapper
public interface RoleUserMapper extends CommonMapper<RoleUser> {

}
