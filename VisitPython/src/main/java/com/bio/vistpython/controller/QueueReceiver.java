package com.bio.vistpython.controller;

import com.bio.vistpython.httpClient.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//消费者，用来接受Flask传来的消息
@Component
@Slf4j
public class QueueReceiver {
    @Autowired
    HttpClient httpClient;
    //使用Message也就是RabbitMQ自带的可以转换自动编码，因为从Python传来队列中的信息是一个ASCII码
    @RabbitListener(queues = "testqueue")
    public void process(Message message){
        String messageBody = new String(message.getBody());
        log.info("传递进来的消息是：" + messageBody);
        //这里的消息应该是一组地址列表，不是一个一个传单独的地址

        //发送给WebSocket 由WebSocket推送给前端
        //开始下载
        //文件的位置
        //方式1：调用网址下载
        //方式2：WebSocket:WebSocket是HTML5新增的协议，让浏览器和服务器之间可以建立无限制的全双工通信，任何一方都可以主动发消息给对方。

    }
}
