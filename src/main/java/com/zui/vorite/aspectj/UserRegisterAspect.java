package com.zui.vorite.aspectj;

import com.zui.vorite.config.RabbitConfig;
import com.zui.vorite.pojo.User;
import com.zui.vorite.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @file: UserRegisterAspect.class
 * @author: Dusk
 * @since: 2018/12/2 22:47
 * @desc: 用户创建时定义的切面
 */

@Aspect
@Component
public class UserRegisterAspect {

    private UserService userService;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Pointcut("execution(* com.zui.vorite.service.impl.UserServiceImpl.insertOrUpdate(com.zui.vorite.pojo.User)) && args(user)")
    public void register(User user){}

    /**
     *  切面，user类， userService缓存取出password写入queue，后清除
     * @param jp
     * @param user
     * @return
     */
    @Around("register(user)")
    public Object bandMail(ProceedingJoinPoint jp, User user){
        Object result = null;
        User registerUser = user;
        try {
            result = jp.proceed();
            String passward = userService.cacheGetPassward(registerUser.getEmail());
            Map<String, Object> context = new HashMap<>(4);
            context.put("username", registerUser.getUsername());
            context.put("password", passward);
            context.put("receiver", registerUser.getEmail());
            context.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            // 发送注册邮件委托给rabbitmq队列
            this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_MAIL_EXCHANGE, "mail.register", context);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            userService.cacheDeletePassward(registerUser.getEmail());
        }
        return result;
    }
}
