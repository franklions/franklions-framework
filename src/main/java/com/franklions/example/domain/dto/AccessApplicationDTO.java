package com.franklions.example.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
@Data
public class AccessApplicationDTO implements Serializable {
    private Long id;
    private String appId;
    private String appName;
    private String remark;
    private Boolean disabled;
    private Long gmtCreated;
    private Long gmtModified;
}
