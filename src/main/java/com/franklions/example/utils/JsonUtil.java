package com.franklions.example.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.StringWriter;
import java.text.SimpleDateFormat;

public class JsonUtil {

    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    public static String toJson(Object entity) {
        return toJson(entity, false);
    }

    public static String toJson(Object entity, Class<?> serializationView) throws RuntimeException {
        return toJson(entity, false, serializationView);
    }

    public static String toJson(Object entity, boolean prettyPrint) throws RuntimeException {
        return toJson(entity, prettyPrint, null);
    }

    public static String toJson(Object entity, boolean prettyPrint, Class<?> serializationView) throws RuntimeException {
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator jg = OBJECTMAPPER.getFactory().createGenerator(sw);
            if (prettyPrint) {
                jg.useDefaultPrettyPrinter();
            }
            if (serializationView != null) {
                OBJECTMAPPER.writerWithView(serializationView)
                        .writeValue(jg, entity);
            } else {
                OBJECTMAPPER.writeValue(jg, entity);
            }
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String str, Class<T> clazz) throws RuntimeException {
        try {
            return OBJECTMAPPER.readValue(str, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String str, TypeReference<T> t) throws RuntimeException {
        try {
            return OBJECTMAPPER.readValue(str, t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String str, Class<?> collectionClass, Class<?> elementClasses) throws RuntimeException {
        try {
            JavaType type = OBJECTMAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return OBJECTMAPPER.readValue(str, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static {
        /** 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性 **/
        OBJECTMAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /** 设置不使用默认日期类型格式 **/
        OBJECTMAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        /** 设置转换日期类型格式 **/
        OBJECTMAPPER.getSerializationConfig().with(new SimpleDateFormat(DATEFORMAT));
        /** 设置转换时忽略空值 **/
        OBJECTMAPPER.getSerializationConfig().withPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.ALWAYS));
        /** 设置反转时日期类型格式 **/
        OBJECTMAPPER.getDeserializationConfig().with(new SimpleDateFormat(DATEFORMAT));
        /** 设置键值可为非双引号形式 **/
        OBJECTMAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        /** 设置解析器支持解析单引号 **/
        OBJECTMAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        /** 设置解析器支持解析结束符 **/
        OBJECTMAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        /** 设置可以带有转义字符 **/
        OBJECTMAPPER.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        OBJECTMAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OBJECTMAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

}