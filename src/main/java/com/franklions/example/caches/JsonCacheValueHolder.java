package com.franklions.example.caches;

import lombok.Data;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/8/25
 * @since Jdk 1.8
 */
@Data
public class JsonCacheValueHolder<V> {
    private Class clazz;
    private V value;

    public JsonCacheValueHolder() {
    }

    public JsonCacheValueHolder(Class clazz, V value) {
        this.clazz = clazz;
        this.value = value;
    }
}
