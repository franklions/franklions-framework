//package com.franklions.example.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author flsh
// * @version 1.0
// * @date 2021/4/26
// * @since Jdk 1.8
// */
//@Api(tags = "健康检查模块")
//@RestController
//@RequestMapping("/actuator")
//public class ActuatorController {
//
//    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Value("${HOSTNAME:micro-service}")
//    private String hostname;
//
//    @Autowired
//    private DeviceInfoMapper mapper;
//
//    @GetMapping(value = "/health")
//    public ResponseEntity health(){
//        Boolean redis = redisConnection();
//        Boolean mysql = mysqlConnection();
//
//        if(mysql==true&&redis){
//            return new ResponseEntity(HttpStatus.OK);
//        }
//
//        Map map = new HashMap<>();
//        map.put("redis",redis);
//        map.put("mysql",mysql);
//        return new ResponseEntity(map,HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    /**
//     *  @Select("select 1")
//     *  String test();
//     *  String data = mapper.test();
//     *  return data.equals("1");
//     * @return
//     */
//    public boolean mysqlConnection() {
//        try {
//            String data = mapper.test();
//            return data.equals("1");
//        } catch (Exception e) {
//            logger.error("health error>>>"+e.getMessage(),e);
//            return false;
//        }
//    }
//
//    public boolean redisConnection() {
//        try {
//            redisTemplate.opsForValue().set(hostname+"-health",String.valueOf(System.currentTimeMillis()),
//                    10, TimeUnit.MINUTES);
//            return redisTemplate.hasKey(hostname+"-health");
//        } catch (Exception e) {
//            logger.error("health error>>>"+e.getMessage(),e);
//            return false;
//        }
//    }
//}
