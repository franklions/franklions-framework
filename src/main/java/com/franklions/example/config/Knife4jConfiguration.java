package com.franklions.example.config;

import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author flsh
 * @version 1.0
 * @date 2024/1/26
 * @since Jdk 1.8
 */
@Configuration
@EnableSwagger2WebMvc
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
        String groupName="2.X版本";
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                    .title("Framework RESTful APIs")
                    .description("# swagger-bootstrap-ui-demo RESTful APIs")
                    .termsOfServiceUrl("http://www.xx.com/")
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
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
        return docket;
    }
}
