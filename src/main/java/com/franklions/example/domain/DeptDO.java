package com.franklions.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/15
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_dept")
public class DeptDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer num;
    private Integer pid;
    private String pids;
    private String simplename;
    private String fullname;
    private String tips;
    private Integer version;

    /**
     * 放弃与用户关系的维护操作，由用户去维护跟部门的关系
     */
    @OneToMany( mappedBy = "deptDO", cascade ={ CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"deptDO"})
    private Set<UserDO> users;

}
