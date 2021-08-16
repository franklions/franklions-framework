package com.franklions.example.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
public class BeanUtils {
    public static <M> void mergeObject(M target, M source) {
        BeanInfo beanInfo = null;

        try {
            beanInfo = Introspector.getBeanInfo(target.getClass());
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            int propLength = properties.length;

            for(int i = 0; i < propLength; ++i) {
                PropertyDescriptor descriptor = properties[i];
                if (descriptor.getWriteMethod() != null) {
                    Object originalValue = descriptor.getReadMethod().invoke(source);
                    if (originalValue != null) {
                        Object defaultValue = descriptor.getReadMethod().invoke(source);
                        descriptor.getWriteMethod().invoke(target, defaultValue);
                    }
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException("merge objects error", ex);
        }
    }
}
