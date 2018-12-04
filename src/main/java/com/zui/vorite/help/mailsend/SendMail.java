package com.zui.vorite.help.mailsend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @Title 注册返回邮件
 *
 * @file: SendMail.class
 * @author: Dusk
 * @since: 2018/12/2 23:39
 * @desc: 发送thymeleaf email模板文件
 */

@Component
public class SendMail implements Send{

    private JavaMailSender mailSender;

    private TemplateEngine templateEngine;

    /**
     *  从properties中取发送者mail
     */
    @Value(("${spring.mail.username}"))
    private String provider;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void send() {

    }

    public void sendRegisterEmail(Map context) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        Context emailContext = new Context();
        emailContext.setVariables(context);
        String process = templateEngine.process("emailTemplate", emailContext);

        // 发送者
        helper.setFrom(provider);
        // 接收者
        helper.setTo(context.get("receiver").toString());
        // 主题
        helper.setSubject("尊敬的："+context.get("username")+"您好！恭喜您注册成功");
        // 邮件内容
        helper.setText(process, true);
        // 发送邮件
        mailSender.send(mimeMessage);
    }
}
