package com.franklions.example.domain;

import com.franklions.framework.entity.AbstractRootEntity;
import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/2
 * @since Jdk 1.8
 */
@ToString
@Data
@Table(name = "sys_user")
public class UserDO  extends AbstractRootEntity{
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String avatar;
    private String account;
    private String password;
    private String salt;
    private String name;
    private Date birthday;
    private Integer sex;
    private String email;
    private String phone;
    private String roleid;
    private Integer deptid;
    private Integer status;
    private Date createtime;

}
