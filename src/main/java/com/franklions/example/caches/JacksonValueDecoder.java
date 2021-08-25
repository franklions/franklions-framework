package com.franklions.example.caches;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.support.AbstractValueDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/8/25
 * @since Jdk 1.8
 */
public class JacksonValueDecoder extends AbstractValueDecoder {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public JacksonValueDecoder() {
        super(false);
    }

    public JacksonValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    protected Object doApply(byte[] buffer) throws Exception {
        if (buffer != null) {
            String str = new String(buffer);
            // 首先要解析出 JsonCacheValueHolder，然后获取到其中的 realObj 及其类型
            JsonCacheValueHolder cacheValueHolder = OBJECT_MAPPER.readValue(str, JsonCacheValueHolder.class);
            if(cacheValueHolder == null || cacheValueHolder.getValue()==null || cacheValueHolder.getClazz()==null ){
                throw new Exception("jetcache jackson反序列化错误，对象值为空");
            }

            Object value = OBJECT_MAPPER.convertValue(cacheValueHolder.getValue(), cacheValueHolder.getClazz());

            CacheValueHolder holder = new CacheValueHolder();
            holder.setValue(value);
            holder.setAccessTime(System.currentTimeMillis());
            holder.setExpireTime(Long.MAX_VALUE);

            return holder;
        }
        return null;
    }
}
