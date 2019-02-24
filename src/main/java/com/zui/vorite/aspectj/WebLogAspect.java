package com.zui.vorite.aspectj;

import com.zui.vorite.pojo.OperationLog;
import com.zui.vorite.service.OperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private OperationLogService operationLogService;

    private OperationLog operationLog;

    @Autowired
    public void setOperationLogService(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @Autowired
    public void setOperationLog(OperationLog operationLog) {
        this.operationLog = operationLog;
    }

    @Pointcut("execution(public * com.zui.vorite.controller..*.*(..)) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void webLog(){}

    /**
     * 环绕切面 ，记录操作
     * @param jp
     * @return
     */
    @Around(value = "webLog()")
    public Object aroundLog(ProceedingJoinPoint jp){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object result = null;
        try {
/*
            logger.info("URL:" + request.getRequestURI().toString());
            logger.info("HTTP_METHOD:" + request.getMethod().toString());
            logger.info("IP:" + request.getRemoteAddr());
            logger.info("CLASS_METHOD:" + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
            logger.info("ARGS:" + Arrays.toString(jp.getArgs()));
            */
            result = jp.proceed();
            logger.info("RESPONSE:" + result);
            operationLog.setUrl(request.getRequestURI().toString());
            operationLog.setHttpMethod(request.getMethod().toString());
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setClassMethod(jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
            /**
             * 结果集有时会很大注掉先
             */
            // operationLog.setArgs(Arrays.toString(jp.getArgs()));
            // operationLog.setResponse(result.toString());
            operationLogService.insert(operationLog);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
