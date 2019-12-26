package com.franklions.example.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

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
    @Column(name = "id")
    private Integer id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "name")
    private String name;
    private Date birthday;
    private Integer sex;
    private String email;
    private String phone;
    private Integer status;
    private Date createtime;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne(targetEntity = DeptDO.class, cascade ={ CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "deptid")
    @JsonIgnoreProperties({"users"})
    private DeptDO deptDO;
}
