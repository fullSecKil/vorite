package com.zui.vorite.help.handle;

import com.rabbitmq.client.Channel;
import com.zui.vorite.config.RabbitConfig;
import com.zui.vorite.help.mailsend.SendMail;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @file: MailHandle.class
 * @author: Dusk
 * @since: 2018/12/2 22:27
 * @desc: Email的消费者,实现发送
 */

@Component
public class MailHandle implements Handle{

    private SendMail sendMaill;

    @Autowired
    public void setSendMaill(SendMail sendMaill) {
        this.sendMaill = sendMaill;
    }

    @Override
    @RabbitListener(queues = {RabbitConfig.DEFAULT_MAIL_QUEUE})
    public void process(Map mailcontext, Message message, Channel channel){
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try{
            sendMaill.sendRegisterEmail(mailcontext);
            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // TODO 处理失败,重新压入MQ
                channel.basicRecover();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
