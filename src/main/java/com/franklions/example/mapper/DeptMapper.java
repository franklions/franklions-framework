package com.franklions.example.mapper;

import com.franklions.example.domain.entity.DeptDO;
import com.franklions.example.domain.entity.DeptUserDO;
import org.apache.ibatis.annotations.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/15
 * @since Jdk 1.8
 */
@Mapper
public interface DeptMapper extends tk.mybatis.mapper.common.Mapper<DeptDO>{

    @Select("select * from sys_dept where id=#{id}")
    DeptDO selectById(@Param("id") Integer id);

    /**
     * 一对多关系映射
     * @param id
     * @return
     */
    @Results(id = "resultDeptUserMap",value = {
            @Result(property = "id", column = "id", javaType = Integer.class,id = true),
            @Result(property = "num", column = "num", javaType = Integer.class),
            @Result(property = "pid", column = "pid", javaType = Integer.class),
            @Result(property = "pids", column = "pids", javaType = String.class),
            @Result(property = "simplename", column = "simplename", javaType = String.class),
            @Result(property = "fullname", column = "fullname", javaType = String.class),
            @Result(property = "tips", column = "tips", javaType = String.class),
            @Result(property = "version", column = "version", javaType = Integer.class),
            @Result(property = "listUser", column = "id",many = @Many(select = "com.franklions.example.mapper.UserMapper.selectByDeptId"))
    })
    @Select("select * from  sys_dept where id = #{id}")
    DeptUserDO selectDeptUsersById(@Param("id") Integer id);
}
