//package com.franklions.example.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lianwu.CMShopping.Configuration.AppConfigProperties;
//import com.lianwu.CMShopping.DTO.ErrorResult;
//import com.lianwu.CMShopping.DTO.ResponseResult;
//import com.lianwu.CMShopping.DTO.UserInfoDTO;
//import com.lianwu.CMShopping.Enum.EnumApiResult;
//import com.lianwu.CMShopping.Service.UserCenterService;
//import com.lianwu.CMShopping.exception.UserCenterServiceException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//import java.util.Optional;
//
//public class HttpControllerAuthorizeFilter extends OncePerRequestFilter {
//
//    private Logger logger = LoggerFactory.getLogger(HttpControllerAuthorizeFilter.class);
//
//    private PathMatcher pathMatcher = new AntPathMatcher();
//
//    AppConfigProperties properties;
//
//
//    UserCenterService userService;
//
//
//    ObjectMapper objectMapper;
//
//    public HttpControllerAuthorizeFilter(AppConfigProperties properties, UserCenterService userService, ObjectMapper objectMapper) {
//        this.properties = properties;
//        this.userService = userService;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String uri = request.getRequestURI();
//            String method = request.getMethod();
//
//            if("OPTIONS".equalsIgnoreCase(method)){
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String path = request.getRequestURI();
//            if(matchesIgnoredPatterns(path)){
//                logger.info("ignored url,"+path);
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            logger.info("web auth url,"+path);
//
//            String  uid = request.getHeader("x-ailife-api-uid");
//            String token = request.getHeader("x-ailife-api-token");
//
//
//            if(uid == null || uid.isEmpty() || token == null || token.isEmpty()){
//                ResponseResult<Object> result = getObjectResponseResult(new ErrorResult(){{
//                    setErrorCode(400D);
//                    setErrorMessage("登录已过期");
//                }});
//                responseErrorHandler(response,result);
//                return;
//            }
//
//            try {
//                Optional<Long> expireIn = userService.verifyToken(uid,token);
//
//                if(!expireIn.isPresent()){
//                    ResponseResult<Object> result = getObjectResponseResult(new ErrorResult(){{
//                        setErrorCode(400D);
//                        setErrorMessage("登录已过期");
//                    }});
//                    responseErrorHandler(response,result);
//                    return;
//                }
//
//                if(expireIn.get() < 1){
//                    ResponseResult<Object> result = getObjectResponseResult(new ErrorResult(){{
//                        setErrorCode(400D);
//                        setErrorMessage("登录已过期");
//                    }});
//                    responseErrorHandler(response,result);
//                    return;
//                }
//            } catch (UserCenterServiceException e) {
//                ResponseResult<Object> result = getObjectResponseResult(e.getErrorResult());
//                responseErrorHandler(response,result);
//                return;
//            }
//
//            Optional<UserInfoDTO> userOpt = userService.getUserInfo(uid);
//
//            if(userOpt.isPresent()) {
//                String username = userOpt.get().getPhone();
//                if(username == null || username.isEmpty()){
//                    username = userOpt.get().getPhone();
//                }
//                RequestHeaderWrapper requestHeaderWrapper = new RequestHeaderWrapper(request);
//                requestHeaderWrapper.putHeader("x-ailifecare-shopping-username",username);
//                requestHeaderWrapper.putHeader("x-ailifecare-shopping-eid", "ailife_toBusiness");
//                filterChain.doFilter(requestHeaderWrapper, response);
//            }else{
//                filterChain.doFilter(request, response);
//            }
//
//            return;
//
//        } catch (Exception e) {
//            logger.error("HttpFilter Error:" + e.getMessage(), e);
//            ResponseResult<Object> result = getObjectResponseResult(new ErrorResult(){{
//                setErrorCode(400D);
//                setErrorMessage("登录已过期");
//            }});
//            responseErrorHandler(response,result);
//            return;
//        }
//    }
//
//    private ResponseResult<Object> getObjectResponseResult(ErrorResult errorResult) {
//        ResponseResult<Object> result = new ResponseResult<>();
//        result.setResult(EnumApiResult.Fail.getValue());
//        result.setResultMessage( "2010");
//        result.setTS((new Date()).getTime());
//        result.setReturnValue((Object) errorResult.getErrorMessage());
//        return result;
//    }
//
//    protected boolean matchesIgnoredPatterns(String path) {
//        if (this.properties.getIgnoredUrls() == null) {
//            return false;
//        }
//
//        for (String pattern : this.properties.getIgnoredUrls()) {
//            logger.debug("Matching ignored pattern:" + pattern);
//            if (this.pathMatcher.match(pattern, path)) {
//                logger.debug("Path " + path + " matches ignored pattern " + pattern);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void responseErrorHandler(HttpServletResponse response, ResponseResult<Object> errorResult) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET,DELETE,PUT,OPTIONS");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization,x-ailife-api-uid,x-ailife-api-token");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        response.setStatus(HttpStatus.OK.value());
//        try {
//            response.getWriter().write(objectMapper.writeValueAsString(errorResult));
//            response.getWriter().flush();
//            response.getWriter().close();
//        } catch (IOException e) {
//            logger.error("HttpFilter response error:" + e.getMessage(), e);
//        }
//
//    }
//}
