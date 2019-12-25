package com.franklions.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-11
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class DeptDTO {
    private Integer id;
    private Integer num;
    private Integer pid;
    private String pids;
    private String simplename;
    private String fullname;
    private String tips;
    private Integer version;

    Set<UserDTO> users;
}
