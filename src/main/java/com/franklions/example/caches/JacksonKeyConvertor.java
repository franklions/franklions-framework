package com.franklions.example.caches;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.function.Function;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/8/25
 * @since Jdk 1.8
 */
public class JacksonKeyConvertor implements Function<Object,Object> {

    public static final Function<Object, Object> INSTANCE = new JacksonKeyConvertor();
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JacksonKeyConvertor() {
    }

    @SneakyThrows
    @Override
    public Object apply(Object key) {
        if (key == null) {
            return null;
        }

        if(key instanceof String){
            return key;
        }

        return OBJECT_MAPPER.writeValueAsString(key);
    }
}
