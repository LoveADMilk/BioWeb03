package com.bio.vistpython.controller;

import com.alibaba.fastjson.JSONObject;
import com.bio.vistpython.httpClient.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndPoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端，
 * 注解的值将被用于监听用户连接的终端访问URL地址，客户端可以通过这个URL连接到websocket服务器端
 */
//注意这里是训练页面的一个子内容，将restTemplate访问Flask传入用户ID的操作放在Controller即可
@Slf4j
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSockTest {

    private static int onlineCount=0;

    private static ConcurrentHashMap<String, WebSockTest> webSocketMap = new ConcurrentHashMap<>();
//    与某个客户端的连接会话，需要通过它来给客户端发送数据

    private Session session;

//    private String userId="";
    @RabbitListener(queues = "testqueue2")
    public void process(Message message){
        System.out.println(message.getBody().getClass());
        String messageBody = new String(message.getBody());
        JSONObject jsonObject = JSONObject.parseObject(messageBody);
        String id = jsonObject.getString("userId");
        String status = jsonObject.getString("status");
        //通过消息对俄返回训练的状态通知前端，现在处于什么阶段
        //阶段1 ： 正在上传
        //阶段2 ： 上传成功，正在编码
        //阶段3 ： 编码成功，正在进行预测
        //阶段4 ： 预测结果为

        onMessage(status, id);//指定编号
//        log.info("传递进来的消息是：" + messageBody);

    }
//    创建连接时触发,并为当前链接（也就是即便是同一台电脑和用户，只要新开一个链接就是新开一个session）
//    创建一个唯一的session
    //利用userId指定到唯一用户之间通信
//    PathParam是webSocket的注解
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId){
        //当用进入页面后自动开启websocket链接
        //防止一个用户新开多个连接，所以需要判断，用户是否当前存在map中
        if(!webSocketMap.containsKey(userId)){
            this.session = session;
            System.out.println("当前用户的sessionID: " + session.getId());
            System.out.println("当前用户的ID: "+ userId);
            webSocketMap.put(userId, this);
            addOnlineCount();
            System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());
        }else{
            log.info("当前用户 "+userId+" 新开链接，不用处理");
        }



    }
//连接断开时触发
    @OnClose
    public void onClose(@PathParam(value = "userId") String userId){
        webSocketMap.remove(userId);
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

//    接收到信息时触发
    @OnMessage
    public void onMessage(String message, @PathParam(value = "userId") String userId){
        System.out.println("来自客户端的消息："+message);
        System.out.println("当前用户的ID: "+ userId);

//        利用线程池限制开启webSocket的数量
        //        指定到确定用户
        if (webSocketMap.containsKey(userId)){
            WebSockTest item = webSocketMap.get(userId);
            try {

                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            log.info("该用户断开链接没法发送onMessage");
        }
    }

//    通讯异常时触发
    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("发生错误！");
        throwable.printStackTrace();
    }
    //   下面是自定义的一些方法
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){
        WebSockTest.onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        WebSockTest.onlineCount--;
    }
}
