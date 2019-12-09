package com.franklions.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResult {
    private Integer errorCode;
    private String errorMessage;
}
