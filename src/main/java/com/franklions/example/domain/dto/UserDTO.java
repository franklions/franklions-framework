package com.franklions.example.domain.dto;

import com.franklions.example.domain.entity.UserDO;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/2
 * @since Jdk 1.8
 */
@Data
public class UserDTO {

    private static final Logger logger = LoggerFactory.getLogger(UserDTO.class);

    private Integer id;
    private String avatar;
    private String account;
    private String name;
    private Date birthday;
    private Integer sex;
    private String email;
    private String phone;
    private String deptName;

    public UserDTO() {
    }

    public UserDTO(UserDO userDO) {
        logger.debug(userDO.toString());

        BeanUtils.copyProperties(userDO,this);
    }

}
