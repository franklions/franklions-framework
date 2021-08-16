package com.franklions.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
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

    public ErrorResult(Object[] error){
        this.errorCode = Integer.valueOf(error[0].toString());
        this.errorMessage = error[1].toString();
    }

}
