package com.franklions.example.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/26
 * @since Jdk 1.8
 */
@Data
public class LoginInfo {

    private Long userId;
    private List<Long> dataScope;

}
