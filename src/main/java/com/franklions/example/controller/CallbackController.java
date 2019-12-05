package com.franklions.example.controller;

import com.franklions.example.domain.CallbackDataRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接收回调消息
 * @author flsh
 * @version 1.0
 * @date 2019-04-10
 * @since Jdk 1.8
 */
@RestController
public class CallbackController {
    Logger logger = LoggerFactory.getLogger(CallbackController.class);
    @PostMapping(value = "/callback")
    public ResponseEntity<?> callback(@RequestBody CallbackDataRequest request){
        logger.error(request.toString());
        return ResponseEntity.ok("SUCCESS");
    }
}
