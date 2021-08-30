package com.franklions.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/1/22
 * @since Jdk 1.8
 */
@Data
@ConfigurationProperties(prefix = "example.api")
public class AppConfigProperties {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private Long tokenExpired;

    private Set<String> ignoredUrls = new LinkedHashSet<>();

    private String privateKey;

    private String publicKey;

}
