package com.franklions.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.filter.HttpLogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-04-08
 * @since Jdk 1.8
 */
@Configuration
@EnableConfigurationProperties(value = {AppConfigProperties.class})
public class AppConfig {

    @Autowired
    private AppConfigProperties properties;

//    @Bean
//    @Qualifier("apiControllerFilterRegister")
//    public FilterRegistrationBean apiControllerRegistFilter(UserCenterService tokenService, ObjectMapper objectMapper, AppConfigProperties properties) {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new HttpControllerAuthorizeFilter(properties,tokenService,objectMapper));
//        registration.addUrlPatterns("/v0/api/*");
//        registration.setName("HttpControllerAuthorizeFilter");
//        registration.setOrder(Integer.MIN_VALUE);
//
//        return registration;
//    }

    @Bean
    public FilterRegistrationBean httpLogFilter() {
        FilterRegistrationBean<GenericFilterBean> registration = new FilterRegistrationBean();
        HttpLogFilter httpLogFilter = new HttpLogFilter(properties);
        registration.setFilter(httpLogFilter);
        registration.addUrlPatterns(new String[]{"/*"});
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
