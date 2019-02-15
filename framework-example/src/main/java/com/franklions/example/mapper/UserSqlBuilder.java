package com.franklions.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/11
 * @since Jdk 1.8
 */
public  class UserSqlBuilder {
    /**
     * If not use @Param, you should be define same arguments with mapper method
      */
    public static String buildGetUsersByName(
           @Param("name") final String name) {
        return new SQL(){{
            SELECT("*");
            FROM("sys_user");
            WHERE("name like CONCAT(#{name}, '%')");
            ORDER_BY("id");
        }}.toString();
    }

}
