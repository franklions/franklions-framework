package com.franklions.example.mapper;

import com.franklions.example.domain.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/15
 * @since Jdk 1.8
 */
@Mapper
public interface RoleMapper  extends tk.mybatis.mapper.common.Mapper<RoleDO> {

    @Select("select * from sys_role where id=#{id}")
    RoleDO selectById(@Param("id") Integer id);
}
