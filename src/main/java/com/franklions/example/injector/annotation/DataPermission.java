package com.franklions.example.injector.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/26
 * @since Jdk 1.8
 */
@Documented
@Target({METHOD,TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface DataPermission {
    /**
     * 是否要进行数据权限隔离
     */
    boolean isPermi() default true;
}
