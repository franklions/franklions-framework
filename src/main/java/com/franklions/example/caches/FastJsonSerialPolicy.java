package com.franklions.example.caches;

import com.alicp.jetcache.anno.SerialPolicy;

import java.util.function.Function;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/8/25
 * @since Jdk 1.8
 */
public class FastJsonSerialPolicy implements SerialPolicy {
    @Override
    public Function<Object, byte[]> encoder() {
        return new FastjsonValueEncoder(false);
    }

    @Override
    public Function<byte[], Object> decoder() {
        return new FastjsonValueDecoder(false);
    }
}
