package com.franklions.example.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2024/4/2
 * @since Jdk 1.8
 */
@Data
public class UserProfile {
    private List<String> permissions;
}
