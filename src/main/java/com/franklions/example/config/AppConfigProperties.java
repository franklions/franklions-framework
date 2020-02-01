package com.franklions.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/1/22
 * @since Jdk 1.8
 */
@Data
@ConfigurationProperties(prefix = "example")
public class AppConfigProperties {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    private String tokenPrefix;
}
