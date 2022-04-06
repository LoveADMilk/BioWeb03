package com.bio.vistpython.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//消费者，用来接受Flask传来的消息
@Component
@Slf4j
public class QueueReceiver {
    //使用Message也就是RabbitMQ自带的可以转换自动编码，因为从Python传来队列中的信息是一个ASCII码
    @RabbitListener(queues = "testqueue")
    public void process(Message message) {
        String messageBody = new String(message.getBody());
        log.info("传递进来的消息是：" + messageBody);
    }
}
