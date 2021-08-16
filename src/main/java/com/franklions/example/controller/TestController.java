package com.franklions.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/8/16
 * @since Jdk 1.8
 */
@RestController
public class TestController extends BaseController {

    @GetMapping("/test")
    public String test(){
        return "SUCCESS";
    }
}
