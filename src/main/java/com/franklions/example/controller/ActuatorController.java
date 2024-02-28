package com.franklions.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/4/26
 * @since Jdk 1.8
 */
@Api(tags = "健康检查模块")
@RestController
@RequestMapping("/actuator")
public class ActuatorController {

    @Value("${health.enabled}")
    private Boolean enabled;


    @ApiOperation(value = "健康检查")
    @GetMapping("/health")
    public ResponseEntity<String> health(){
        if(enabled) {
            return ResponseEntity.ok("SUCCESS");
        }else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
