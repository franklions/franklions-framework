package com.franklions.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * k8s健康检查
 * Author xdb
 * Date 2024/06/21
 **/
@RestController
public class HealthController {
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

//    @Autowired
//    private MongoTemplate mongoTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 健康检查接口
     */
    @GetMapping(value = "/health")
    public ResponseEntity health() {
        Map<String, Boolean> healthStatus = new HashMap<>();
        healthStatus.put("redis", checkConnection(this::redisConnection));
        healthStatus.put("mysql", checkConnection(this::mysqlConnection));
//        healthStatus.put("mongoDB", checkConnection(this::mongoConnection));

        if (healthStatus.values().stream().allMatch(Boolean::booleanValue)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(healthStatus, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 检查连接状态并记录异常
     *
     * @param connectionChecker 连接检查逻辑
     * @return 是否连接成功
     */
    private boolean checkConnection(RunnableBooleanSupplier connectionChecker) {
        try {
            return connectionChecker.getAsBoolean();
        } catch (Exception e) {
            logger.error("Connection check failed: ", e);
            return false;
        }
    }

    /**
     * Redis连接检查
     */
    private boolean redisConnection() {
        return Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            String pingResponse = connection.ping();
            return "PONG".equals(pingResponse);
        }));
    }

    /**
     * MySQL连接检查
     */
    private boolean mysqlConnection() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result == null) {
                logger.error("MySQL connection check returned null result");
                return false;
            }
            return result == 1;
        } catch (Exception e) {
            logger.error("MySQL connection check failed: ", e);
            return false;
        }
    }

//    /**
//     * MongoDB连接检查
//     */
//    private boolean mongoConnection() {
//        try {
//            mongoTemplate.executeCommand("{ ping: 1 }");
//            return true;
//        } catch (Exception e) {
//            logger.error("MongoDB connection check failed: ", e);
//            return false;
//        }
//    }


    /**
     * 自定义函数式接口，用于封装布尔值返回的逻辑
     */
    @FunctionalInterface
    interface RunnableBooleanSupplier {
        boolean getAsBoolean() throws Exception;
    }

}
