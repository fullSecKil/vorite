package com.zui.vorite.help.handle;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.util.Map;

/**
 * @file: Handle.class
 * @author: Dusk
 * @since: 2018/12/3 15:36
 * @desc:
 */
public interface Handle {
    void process(Map context, Message message, Channel channel);
}
