package com.franklions.example.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
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
@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class UserDO  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer status;
    private Date createtime;

    @ManyToOne(targetEntity = DeptDO.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "deptid")
    @JsonIgnoreProperties({"users"})
    private DeptDO deptDO;
}
