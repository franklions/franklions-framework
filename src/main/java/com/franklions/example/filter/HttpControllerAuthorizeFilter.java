package com.franklions.example.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.config.AppConfigProperties;
import com.franklions.example.exception.ErrorCode;
import com.franklions.example.exception.ErrorResult;
import com.franklions.example.service.AccessTokenService;
import com.franklions.example.utils.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
 * @date 2020/2/2
 * @since Jdk 1.8
 */
public class HttpControllerAuthorizeFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(HttpControllerAuthorizeFilter.class);
    private PathMatcher pathMatcher = new AntPathMatcher();
    private AppConfigProperties properties;
    private ObjectMapper objectMapper;
    private AccessTokenService accessTokenService;

    public HttpControllerAuthorizeFilter(AppConfigProperties properties, AccessTokenService tokenService, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.accessTokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String uri = request.getRequestURI();
            String method = request.getMethod();

            if("OPTIONS".equalsIgnoreCase(method)){
                filterChain.doFilter(request, response);
                return;
            }

            String path = request.getRequestURI();
            if(matchesIgnoredPatterns(path)){
                logger.info("ignored url,"+path);
                filterChain.doFilter(request, response);
                return;
            }

            logger.info("web auth url,"+path);

            /**/
            //TODO 权限验证过程
//            String  base64Auth = request.getHeader("x-daxin-authorization");
//            String clientId = request.getHeader("x-daxin-saas-clientId");
//            if(StringUtils.isBlank(base64Auth) ||StringUtils.isBlank(clientId) ){
//
//                responseErrorHandler(response,new ErrorResult(ErrorCode.USER_UNAUTHORIZED));
//                return;
//            }
//
//            String authStr = new String(Base64.getDecoder().decode(base64Auth), "UTF-8");
//
//            String[] authInfo = authStr.split(":");
//            if(authInfo.length != 4){
//
//                responseErrorHandler(response,new ErrorResult(ErrorCode.USER_UNAUTHORIZED));
//                return;
//            }
//
//            /*openapi saasapi*/
//            String apiType = authInfo[0];
//            String eid = authInfo[1];
//            String uid = authInfo[2];
//            String token = authInfo[3];
//            if(StringUtils.isBlank(uid)||StringUtils.isBlank(token)){
//
//                responseErrorHandler(response,new ErrorResult(ErrorCode.USER_UNAUTHORIZED));
//                return;
//            }
//
//            Optional<Long> expireIn = accessTokenService.verifyToken(eid,uid,token,clientId);
//
//            if(!expireIn.isPresent()){
//
//                responseErrorHandler(response,new ErrorResult(ErrorCode.USER_TOKEN_EXPIRE));
//                return;
//            }
//
//            if(expireIn.get() < 1){
//
//                responseErrorHandler(response,new ErrorResult(ErrorCode.USER_TOKEN_EXPIRE));
//                return;
//            }

            if (ServletUtil.isMultiPartRequest(request)) {
                RequestHeaderWrapper requestHeaderWrapper = new RequestHeaderWrapper(request);
//                requestHeaderWrapper.putHeader("x-yscloud-saas-eid", eid);
//                requestHeaderWrapper.putHeader("x-yscloud-saas-uid", uid);

                filterChain.doFilter(requestHeaderWrapper, response);
            }else{
                RequestParameterWrapper requestParamsWrapper = new RequestParameterWrapper(request);
//                requestParamsWrapper.putHeader("x-yscloud-saas-eid", eid);
//                requestParamsWrapper.putHeader("x-yscloud-saas-uid", uid);
//                if (StringUtils.isNotEmpty(uid) && !properties.getAppPersonClientId().equals(clientId)&& !properties.getAppHotelClientId().equals(clientId)) {
//                    List<String> serviceIds = accessTokenService.getServiceByUid(eid, uid);
//                    String[] serviceArray=null;
//                    if (serviceIds==null || serviceIds.size()<1){
//                        serviceArray=null;
//                    }else {
//                        serviceArray= serviceIds.toArray(new String[0]);
//                    }
//                    requestParamsWrapper.addParameter("serviceIds", serviceArray);
//                }
                filterChain.doFilter(requestParamsWrapper, response);
            }
            return;

        } catch (Exception e) {
            logger.error("HttpFilter Error:" + e.getMessage(), e);

            responseErrorHandler(response,new ErrorResult(ErrorCode.USER_UNAUTHORIZED));
            return;
        }
    }



    protected boolean matchesIgnoredPatterns(String path) {
        if (this.properties.getIgnoredUrls() == null) {
            return false;
        }

        for (String pattern : this.properties.getIgnoredUrls()) {
            logger.debug("Matching ignored pattern:" + pattern);
            if (this.pathMatcher.match(pattern, path)) {
                logger.debug("Path " + path + " matches ignored pattern " + pattern);
                return true;
            }
        }
        return false;
    }

    private void responseErrorHandler(HttpServletResponse response, ErrorResult errorResult) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,DELETE,PUT,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization,x-ailife-authorization,x-yscloud-saas-clientId,x-yscloud-saas-eid,x-yscloud-saas-uid");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResult));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("HttpFilter response error:" + e.getMessage(), e);
        }

    }
}