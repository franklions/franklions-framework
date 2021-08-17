package com.franklions.example.mapper;

import com.franklions.example.domain.entity.MenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-02-16
 * @since Jdk 1.8
 */
@Mapper
public interface MenuMapper extends tk.mybatis.mapper.common.Mapper<MenuDO>{

    @Select("select * from sys_menu where id=#{id}")
    MenuDO selectById(@Param("id") Long id);

    @Select("select * from sys_menu inner join sys_relation on sys_menu.id=sys_relation.menuid where sys_relation.roleid=#{roleid}")
    List<MenuDO> selectByRoleId(@Param("roleid") Integer roleid);
}
