package com.franklions.example.filter;


import com.franklions.example.config.AppConfigProperties;
import com.franklions.example.handler.HttpLogHandler;
import com.franklions.example.utils.ServletUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
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
    private PathMatcher pathMatcher = new AntPathMatcher();


    public HttpLogFilter(AppConfigProperties properties) {
        this.properties = properties;
        this.httpLogHandler = new HttpLogHandler(properties);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //上传文件不记录日志
        if (ServletUtil.isMultiPartRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String api = ServletUtil.getApi(request, this.properties.getContextPath());
        if(matchesIgnoredPatterns(api)){
            filterChain.doFilter(request, response);
            return;
        }
        RequestWrapper requestWrapper = new RequestWrapper(request);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        String reqBody = IOUtils.toString(requestWrapper.getBody(), request.getCharacterEncoding());

        String token = ServletUtil.getToken(request, "");
        String params = ServletUtil.getParams(request);
        // 记录头部日志需要以"x-"开头
        String headers = ServletUtil.getHeaders(request);

        try {

            this.httpLogHandler.logRequest(api, headers+"|"+params+"|"+reqBody,token);
            filterChain.doFilter(requestWrapper, responseWrapper);
            String respBody = "IS NOT JSON";
            if (ServletUtil.isJsonResponse(response)) {
                respBody = IOUtils.toString(responseWrapper.toByteArray(), responseWrapper.getCharacterEncoding());
            }else if(ServletUtil.isTextResponse(response)){
                respBody = new String(responseWrapper.toByteArray());
            }

            this.httpLogHandler.logResponse(api, respBody, token);
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        }
    }

    protected boolean matchesIgnoredPatterns(String path) {
        if (this.properties.getIgnoredLogUrls() == null) {
            return false;
        }

        for (String pattern : this.properties.getIgnoredLogUrls()) {
            logger.debug("Matching ignored pattern:" + pattern);
            if (this.pathMatcher.match(pattern, path)) {
                logger.debug("Path " + path + " matches ignored pattern " + pattern);
                return true;
            }
        }
        return false;
    }
}
