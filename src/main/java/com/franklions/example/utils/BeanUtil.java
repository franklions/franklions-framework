package com.franklions.example.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-25
 * @since Jdk 1.8
 */
public class BeanUtil {

    /**
     * 合并实体属性，仅复制目标值为空的值
     * @param target
     * @param source
     * @param <E>
     */
    public static <E> void mergeObject(E target,E source)  {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                if (propertyDescriptor.getWriteMethod() != null) {
                    Object originalValue = propertyDescriptor.getReadMethod().invoke(target);
                    //仅复制目标值为空的值
                    if (originalValue == null) {
                        Object defaultValue = propertyDescriptor.getReadMethod().invoke(source);
                        propertyDescriptor.getWriteMethod().invoke(target, defaultValue);
                    }
                }
            }
        }catch (Exception ex){
            throw new RuntimeException("merge objects error", ex);
        }
    }
}
