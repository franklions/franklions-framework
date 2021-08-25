package com.franklions.example.caches;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.anno.SerialPolicy;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/8/25
 * @since Jdk 1.8
 */
public class JacksonSerialPolicy implements SerialPolicy {
    public static final String JACKSON="JACKSON";

    @Override
    public Function<Object, byte[]> encoder() {
        return new JacksonValueEncoder();
    }

    @Override
    public Function<byte[], Object> decoder() {
         return new JacksonValueDecoder();
    }
}
