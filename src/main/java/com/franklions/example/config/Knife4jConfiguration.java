package com.franklions.example.config;

import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2024/1/26
 * @since Jdk 1.8
 */
@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)        //导入其他的配置类 让配置生效
public class Knife4jConfiguration {

    private final TypeResolver typeResolver;
    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public Knife4jConfiguration(TypeResolver typeResolver, OpenApiExtensionResolver openApiExtensionResolver) {
        this.typeResolver = typeResolver;
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        String groupName="1.X版本";
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                    .title("某某系统接口文档")
                    .description("# 某某系统接口文档")
                    .termsOfServiceUrl("https://www.lianwukeji.com/")
                    .contact("xx@qq.com")
                    .version("1.0")
                    .build())
                //分组名称
                .groupName(groupName)
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.franklions.example.controller"))
                .paths(PathSelectors.any())
                .build()
                .enableUrlTemplating(false)
                .extensions(openApiExtensionResolver.buildExtensions(groupName))
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .globalResponseMessage(RequestMethod.POST,responseMessageList())
                .globalResponseMessage(RequestMethod.GET,responseMessageList())
                .globalResponseMessage(RequestMethod.PUT,responseMessageList())
                .globalResponseMessage(RequestMethod.DELETE,responseMessageList());
        return docket;
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> list = new ArrayList<>();
         list.add(new ApiKey("BearerToken", "x-Authorization", "header"));
         return list;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> list = new ArrayList<>();
        list.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                //.forPaths(PathSelectors.regex(".*?208.*$"))
                .build());
        return list;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> list = new ArrayList<>();
        list.add(new SecurityReference("BearerToken", authorizationScopes));
        return list;
    }

    private List<ResponseMessage> responseMessageList() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(200).message("请求成功").build());
//        responseMessageList.add(new ResponseMessageBuilder().code(201).message("Created").build());
        responseMessageList.add(new ResponseMessageBuilder().code(400).message("请求参数错误").build());
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("验证未通过").build());
        responseMessageList.add(new ResponseMessageBuilder().code(403).message("禁用").build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("资源未找到").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("内部服务器错误").build());
        return responseMessageList;
    }
}
