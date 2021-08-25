package com.franklions.example.config;

import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.caches.FastJsonSerialPolicy;
import com.franklions.example.caches.JacksonKeyConvertor;
import com.franklions.example.caches.JacksonSerialPolicy;
import com.franklions.example.filter.HttpControllerAuthorizeFilter;
import com.franklions.example.filter.HttpLogFilter;
import com.franklions.example.service.AccessTokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;

import java.util.function.Function;

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

    /**
     * jackson缓存序列化方法
     * @return
     */
    @Bean(name = "jacksonPolicy")
    public JacksonSerialPolicy jsonSerializerPolicy() {
        return new JacksonSerialPolicy();
    }

    public static final String JACKSON = "jackson";
    public static final String FASTJSON = "fastjson";

    /**
     重写 SpringConfigProvider 中的编码和解码

     **/
    @Bean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider() {

            @Override
            public Function<byte[], Object> parseValueDecoder(String valueDecoder) {
                if (FASTJSON.equalsIgnoreCase(valueDecoder)) {
                    return new FastJsonSerialPolicy().decoder();
                } else if (JACKSON.equalsIgnoreCase(valueDecoder)) {
                    return new JacksonSerialPolicy().decoder();
                }
                return super.parseValueDecoder(valueDecoder);
            }

            @Override
            public Function<Object, byte[]> parseValueEncoder(String valueEncoder) {
                if (FASTJSON.equalsIgnoreCase(valueEncoder)) {
                    return new FastJsonSerialPolicy().encoder();
                } else if (JACKSON.equalsIgnoreCase(valueEncoder)) {
                    return new JacksonSerialPolicy().encoder();
                }
                return super.parseValueEncoder(valueEncoder);
            }

            @Override
            public Function<Object, Object> parseKeyConvertor(String convertor) {
                if (JACKSON.equalsIgnoreCase(convertor)) {
                    return JacksonKeyConvertor.INSTANCE;
                }
                return super.parseKeyConvertor(convertor);
            }
        };
    }
}
