package com.franklions.example.domain;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public UserDTO() {
    }

    public UserDTO(UserDO userDO) {
        logger.debug(userDO.toString());

        this.id = userDO.getId();
        this.avatar = userDO.getAvatar();
        this.account= userDO.getAccount();
        this.name = userDO.getName();
        this.birthday = userDO.getBirthday();
        this.sex = userDO.getSex();
        this.email = userDO.getEmail();
        this.phone = userDO.getPhone();
    }

}
