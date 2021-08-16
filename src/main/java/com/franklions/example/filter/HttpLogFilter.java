package com.franklions.example.filter;


import com.franklions.example.config.AppConfigProperties;
import com.franklions.example.handler.HttpLogHandler;
import com.franklions.example.utils.ServletUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/1/22
 * @since Jdk 1.8
 */
public class HttpLogFilter extends OncePerRequestFilter {
    private HttpLogHandler httpLogHandler;
    private AppConfigProperties properties;


    public HttpLogFilter(AppConfigProperties properties) {
        this.properties = properties;
        this.httpLogHandler = new HttpLogHandler(properties);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        String reqBody;
        if (ServletUtil.isMultiPartRequest(request)) {
            reqBody = "MULTIPART";
        } else {
            reqBody = IOUtils.toString(requestWrapper.getBody(), request.getCharacterEncoding());
        }

        String api = ServletUtil.getApi(request, this.properties.getContextPath());
        String token = ServletUtil.getToken(request, "");
        String params = ServletUtil.getParams(request);

        try {

            this.httpLogHandler.logRequest(api, params+"|"+reqBody,token);
            filterChain.doFilter(requestWrapper, responseWrapper);
            String respBody = "IS NOT JSON";
            if (ServletUtil.isJsonResponse(response)) {
                respBody = IOUtils.toString(responseWrapper.toByteArray(), responseWrapper.getCharacterEncoding());
            }

            this.httpLogHandler.logResponse(api, respBody, token);
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        }
    }
}
