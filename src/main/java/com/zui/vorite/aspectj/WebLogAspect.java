package com.zui.vorite.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @file: WebLogAspect.class
 * @author: Dusk
 * @since: 2018/12/3 17:53
 * @desc:
 */

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = Logger.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.zui.vorite.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("URL:" + request.getRequestURI().toString());
        logger.info("HTTP_METHOD:" + request.getMethod().toString());
        logger.info("IP:" + request.getRemoteAddr());
        logger.info("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS:" + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret){
        logger.info("RESPONSE:" + ret);
    }
}
