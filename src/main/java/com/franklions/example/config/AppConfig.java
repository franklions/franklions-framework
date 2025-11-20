package com.franklions.example.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.filter.HttpControllerAuthorizeFilter;
import com.franklions.example.filter.HttpLogFilter;
import com.franklions.example.service.AccessTokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;

import java.util.Collections;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019-04-08
 * @since Jdk 1.8
 */
@Configuration
@EnableConfigurationProperties(value = {AppConfigProperties.class})
public class AppConfig {

    @Bean(name = "apiControllerFilterRegister")
    @ConditionalOnClass({AccessTokenService.class})
    public FilterRegistrationBean apiControllerRegistrationFilter(AccessTokenService tokenService,
                                                                  AppConfigProperties properties,
                                                                  ObjectMapper objectMapper) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpControllerAuthorizeFilter(properties,tokenService,objectMapper));
        registration.setName("HttpControllerAuthorizeFilter");
        registration.setOrder(Integer.MIN_VALUE);

        return registration;
    }

    @Bean
    public FilterRegistrationBean httpLogFilter(AppConfigProperties properties) {
        FilterRegistrationBean<GenericFilterBean> registration = new FilterRegistrationBean();
        HttpLogFilter httpLogFilter = new HttpLogFilter(properties);
        registration.setFilter(httpLogFilter);
        registration.addUrlPatterns(new String[]{"/*"});
        registration.setOrder(3);
        return registration;
    }

    /**
     * Credentials 与通配符冲突
     * allowCredentials(true) 时，allowedOrigins 不能为 *，需明确指定域名。
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许发送凭证（如 Cookie）
//        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 允许的源
//        config.setAllowedMethods(Collections.singletonList("*")); // 允许所有方法（GET/POST等）
//        config.setAllowedHeaders(Collections.singletonList("*")); // 允许所有请求头
//        config.setExposedHeaders(Collections.singletonList("*")); // 暴露所有响应头
        config.addAllowedOriginPattern("*");    // 允许所有源
        config.addAllowedHeader("*");       // 允许所有请求头
        config.addAllowedMethod("*");       // 允许所有方法
        config.addExposedHeader("*");       // 暴露所有响应头

        config.setMaxAge(3600L); // 预检请求缓存时间（单位：秒）

        // 2. 为指定 URL 路径应用 CORS 配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 覆盖所有路径

        // 3. 创建 CorsFilter 并注册
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

        // 4. 设置过滤器优先级（确保在 Spring Security 过滤器之前）
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 对未知属性时行忽略
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper;
    }

    /**
     *
     *〈简述〉修复同一时间无法执行多个 定时任务问题
     *〈详细描述〉
     * @author miaoShijun
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(50);
        return taskScheduler;
    }
}
