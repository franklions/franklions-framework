package com.franklions.example.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.config.AppConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/1/22
 * @since Jdk 1.8
 */
public class HttpLogHandler {
    private static final String REQUEST = " - Request - ";
    private static final String RESPONSE = " - Response - ";
    private Logger logger = LoggerFactory.getLogger(HttpLogHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private AppConfigProperties properties;
    public HttpLogHandler() {
    }

    public HttpLogHandler(AppConfigProperties properties) {
        this.properties = properties;
    }

    public void logRequest(String api, String body, String token) throws Exception {
        this.flush(" - Request - ", api, body, token);
    }

    public void logResponse(String api, String body, String token) throws Exception {
        this.flush(" - Response - ", api, body, token);
    }

    private String buildLogTxt(String type, String api, String md5Token, String acct, String miniBody) {
        Object[] params = new Object[]{type, api, md5Token, acct, miniBody};
        return MessageFormat.format("{0} [{1}:{2}:{3}] -- {4}", params);
    }

    private void flush(String type, String api, String body, String token) throws Exception {
        String miniBody = "";

        if (!StringUtils.isEmpty(body)) {
            miniBody = objectMapper.writeValueAsString(body);
        }

        this.logger.info(this.buildLogTxt(type, api, token, token, miniBody));
    }
}

