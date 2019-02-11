package com.franklions.example.mapper;

import com.franklions.example.domain.UserDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;


/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/2
 * @since Jdk 1.8
 */
@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<UserDO> {

    @Results(id = "userMap", value = {
            @Result(property = "id", column = "id", javaType = Integer.class, id = true),
            @Result(property = "avatar", column = "avatar", javaType = String.class),
            @Result(property = "account", column = "account", javaType = String.class),
            @Result(property = "password", column = "password", javaType = String.class),
            @Result(property = "salt", column = "salt", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "birthday", column = "birthday", javaType = Date.class),
            @Result(property = "sex", column = "sex", javaType = Integer.class),
            @Result(property = "email", column = "email", javaType = String.class),
            @Result(property = "phone", column = "phone", javaType = String.class),
            @Result(property = "roleid", column = "roleid", javaType = String.class),
            @Result(property = "deptid", column = "deptid", javaType = Integer.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
            @Result(property = "createtime", column = "createtime", javaType = Date.class)
    })
    @SelectProvider(type=UserSqlBuilder.class,method = "buildGetUsersByName")
    List<UserDO> selectByName(@Param("name") String name);

    @ResultMap("userMap")
    @Select("select * from sys_user where `account`=#{account}")
    UserDO selectByAccount(@Param("account") String account);
}
