package com.franklions.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/1/31
 * @since Jdk 1.8
 */
//@EntityScan({"com.syeinfo.alpaca.db.entity", "com.syeinfo.alpaca.db.dto"})
//@EnableJpaRepositories({"com.syeinfo.alpaca.db.repo"})
//@PropertySources({
//        @PropertySource(value = "classpath:redis.properties"),
//        @PropertySource(value = "classpath:datasource.properties"),
//        @PropertySource(value = "classpath:customize.properties")
//})
//@SpringBootApplication(
//        scanBasePackages = {"com.syeinfo.okra", "com.syeinfo.alpaca"}
//)
@SpringBootApplication
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class);
    }
}
