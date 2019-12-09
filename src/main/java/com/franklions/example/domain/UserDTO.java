package com.franklions.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/2
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private static final Logger logger = LoggerFactory.getLogger(UserDTO.class);

    private Integer id;
    private String avatar;

    @NotBlank(message = "帐户不能为空") // 为""/''都不行
    @Size(min = 2, max = 30, message = "2<长度<30")
    private String account;

    @NotBlank(message = "用户名不能为空") // 为""/''都不行
    @Size(min = 2, max = 30, message = "2<长度<30")
    private String name;
    private Date birthday;
    private Integer sex;
    private String email;
    private String phone;
    private String deptName;

    public UserDTO(UserDO userDO) {
        logger.debug(userDO.toString());

        BeanUtils.copyProperties(userDO,this);
    }

}
