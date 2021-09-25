package com.franklions.example.domain.request;

import com.franklions.example.domain.entity.UserDO;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/2
 * @since Jdk 1.8
 */
@Data
public class UserRequest {

    private static final Logger logger = LoggerFactory.getLogger(UserRequest.class);

    private Integer id;
    private String avatar;

    @NotBlank(message = "帐户不能为空") // 为""/''都不行
    @Size(min = 2, max = 30, message = "2<长度<30")
    private String account;

    @NotBlank(message = "用户名不能为空") // 为""/''都不行
    @Size(min = 2, max = 30, message = "2<长度<30")
    private String name;
    private Date birthday;

    @NotBlank(message = "状态不能为空")
    @Pattern(regexp = "^(0|1|2|3|-1)$",message = "状态值不正确")
    private String status;

    private Integer sex;
    private String email;
    private String phone;
    private String deptName;

    public UserRequest() {
    }

    public UserRequest(UserDO userDO) {
        logger.debug(userDO.toString());

        BeanUtils.copyProperties(userDO,this);
    }

}
