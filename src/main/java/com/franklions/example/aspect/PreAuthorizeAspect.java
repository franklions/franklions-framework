package com.franklions.example.aspect;

import com.franklions.example.aspect.annotation.RequiresPermissions;
import com.franklions.example.domain.dto.UserProfile;
import com.franklions.example.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于 Spring Aop 的注解鉴权
 * 
 * @author kong
 */
@Slf4j
@Aspect
@Component
public class PreAuthorizeAspect
{
    @Autowired
    private HttpServletRequest request;


    /**
     * 构建
     */
    public PreAuthorizeAspect()
    {
    }

    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " @annotation(com.franklions.example.aspect.annotation.RequiresPermissions)";

    /**
     * 声明AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut()
    {
    }

    /**
     * 环绕切入
     * 
     * @param joinPoint 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable
    {
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        checkMethodAnnotation(signature.getMethod());
        try
        {
            // 执行原有逻辑
            Object obj = joinPoint.proceed();
            return obj;
        }
        catch (Throwable e)
        {
            throw e;
        }
    }

    /**
     * 对一个Method对象进行注解检查
     */
    public void checkMethodAnnotation(Method method)
    {

        // 校验 @RequiresPermissions 注解
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null)
        {
            String uid = request.getHeader("x-dayi-saas-uid");
            log.info("用户ID:"+uid);
            UserProfile user =  getCatchUser(uid);
            //userPermissions
            AuthUtil.checkPermi(user.getPermissions(), requiresPermissions);
        }
    }

    private UserProfile getCatchUser(String uid) {
        //根据uid获取用户信息
        List<String> list = new ArrayList<>();
        list.add("BASIC_ENTERPRISE");
        list.add("BASIC_REGION");
        list.add("BASIC_TEMPLATE");
        UserProfile userProfile = new UserProfile();
        userProfile.setPermissions(list);
        return userProfile;
    }
}
