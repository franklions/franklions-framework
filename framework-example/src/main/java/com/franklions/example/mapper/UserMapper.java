package com.franklions.example.mapper;

import com.franklions.example.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/2
 * @since Jdk 1.8
 */
@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<UserDO> {
}
