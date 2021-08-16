package com.franklions.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/1/22
 * @since Jdk 1.8
 */
public class ServletUtil {
    public ServletUtil() {
    }

    public static String getChannel(JsonNode body) {
        return body.get("requestHeader").get("serviceContext").get("channel").asText();
    }

    public static int getRespStatus(JsonNode body) {
        return body.get("responseHeader").get("status").asInt();
    }

    public static String getApi(HttpServletRequest request, String contextPath) {
        return request.getRequestURI().replaceFirst(contextPath, "");
    }

    public static boolean isMultiPartRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return !StringUtils.isEmpty(contentType) ? contentType.toLowerCase().contains("multipart/form-data") : false;
    }

    public static boolean isJsonResponse(HttpServletResponse response) {
        String contentType = response.getContentType();
        return !StringUtils.isEmpty(contentType) ? contentType.toLowerCase().contains("application/json") : false;
    }

    public static String getToken(HttpServletRequest request, String tokenPrefix) {
        String token = request.getHeader("Authorization");
        return !StringUtils.isEmpty(token) ? token.replaceFirst(tokenPrefix, "").trim() : null;
    }


    public static String getNonce(HttpServletRequest request) {
        return request.getHeader("x-nonce");
    }

    public static String getIp(HttpServletRequest request) {
        String xri = request.getHeader("X-Real-IP");
        String xff = request.getHeader("X-Forwarded-For");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(xff) && !"unKnown".equalsIgnoreCase(xff)) {
            int index = xff.indexOf(",");
            return index != -1 ? xff.substring(0, index) : xff;
        } else {
            return xri;
        }
    }

    public static JsonNode getJsonNodeBody(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(body);
    }

    public static String getParams(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String[]> paramMap = request.getParameterMap();
        return mapper.writeValueAsString(paramMap);
    }
}
