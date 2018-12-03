package com.zui.vorite.config;

import net.sf.jsqlparser.statement.select.Top;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @file: RabbitConfig.class
 * @author: Dusk
 * @since: 2018/12/2 21:09
 * @desc: RabbitMQ配置，新建队列，路由
 */
@Configuration
public class RabbitConfig {

    public static final String DEFAULT_MAIL_QUEUE = "dev.mail.register.default.queue";
    public static final String MANUAL_MAIL_QUEUE = "dev.mail.register.manual.queue";
    public static final String DEFAULT_MAIL_EXCHANGE= "dev.mail.register.default.exchange";

    @Bean
    public Queue defaultMailQueue(){
        // QUEUE 的名字,持久化
        return new Queue(DEFAULT_MAIL_QUEUE, true);
    }

    @Bean
    public Queue manualMailQueue(){
        return new Queue(MANUAL_MAIL_QUEUE, true);
    }

    @Bean
    /**
     *  交换机
     */
    TopicExchange mailExchange(){
        return new TopicExchange(DEFAULT_MAIL_EXCHANGE);
    }

    @Bean
    public Binding bindingExchangeMessage(Queue defaultMailQueue, TopicExchange mailExchange){
        return BindingBuilder.bind(defaultMailQueue).to(mailExchange).with("mail.register");
    }

    @Bean
    public Binding bindingUrgentExchangeMessage(Queue manualMailQueue, TopicExchange mailExchange){
        return BindingBuilder.bind(manualMailQueue).to(mailExchange).with("mail.urgent#");
    }

}
