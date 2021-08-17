package com.franklions.example.mapper;

import com.franklions.example.domain.entity.RoleDO;
import com.franklions.example.domain.entity.RoleMenusDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    /**
     * 映射多对多的关系
     * @return
     */
    @Results(id = "roleMenusMap", value = {
            @Result(property = "id", column = "id", id=true),
            @Result(property = "num", column = "num"),
            @Result(property = "pid", column = "pid"),
            @Result(property = "name", column = "name"),
            @Result(property = "deptid", column = "deptid"),
            @Result(property = "tips", column = "tips"),
            @Result(property = "version", column = "version"),
            @Result(property = "listMenu", column = "id",
                    many = @Many(select = "com.franklions.example.mapper.MenuMapper.selectByRoleId"))
    })
    @Select("select * from sys_role ")
    List<RoleMenusDO> selectRoleMenus();
}
