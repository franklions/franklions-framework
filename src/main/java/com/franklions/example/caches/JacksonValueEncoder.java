package com.franklions.example.caches;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.support.AbstractValueEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/8/25
 * @since Jdk 1.8
 */
public class JacksonValueEncoder extends AbstractValueEncoder {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public JacksonValueEncoder() {
        super(false);
    }

    public JacksonValueEncoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @SneakyThrows
    @Override
    public byte[] apply(Object o) {
        if (o != null) {
            CacheValueHolder cacheValueHolder = (CacheValueHolder) o;
            Object value = cacheValueHolder.getValue();

            // 为防止出现 Value 无法强转成指定类型对象的异常，这里生成一个 JsonCacheObject 对象，保存目标对象的类型（比如 User）
            JsonCacheValueHolder jsonCacheObject = new JsonCacheValueHolder(value.getClass(), value);
            cacheValueHolder.setValue(jsonCacheObject);
            return OBJECT_MAPPER.writeValueAsString(jsonCacheObject).getBytes();
        }
        return new byte[0];
    }
}
