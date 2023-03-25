package com.franklions.example.injector.annotation;

import java.lang.annotation.*;

/**
 * 表示当插入主键冲突时更新的字段
 * @author flsh
 * @version 1.0
 * @date 2023/3/25
 * @since Jdk 1.8
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface UpdateField {
    /**
     * 数据库字段值
     * @return
     */
    String value() default "";
}
