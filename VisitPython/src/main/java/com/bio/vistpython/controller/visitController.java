package com.bio.vistpython.controller;

import com.bio.common.helper.JwtHelper;
import com.bio.vistpython.httpClient.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class visitController {
    @Autowired
    HttpClient httpClient;

    @Resource
    private WebSockTest webSockTest;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @RequestMapping("/hello")
    public String getFromFlask(){
        String url = "http://127.0.0.1:5000";//Flask启动的默认端口
        HttpMethod httpMethod = HttpMethod.GET;//Flask 在开启端口的时候默认是get访问
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        return httpClient.client(url, httpMethod, params);
    }
// 点击这里实现预测功能。。。
    @RequestMapping("/greet")
    public String greet(Model model, HttpServletRequest request){
        Cookie[] cookies =  request.getCookies();
        String token = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("Biotoken")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if(token.equals("") || JwtHelper.getUserName(token) == null || JwtHelper.getUserName(token).equals("")){
            System.out.println("提醒用户进行登录");
//            return "";
        }

        model.addAttribute("userName", JwtHelper.getUserName(token));
        model.addAttribute("userId", JwtHelper.getUserId(token));
        model.addAttribute("userEmail", JwtHelper.getUserEmail(token));

        //将用户的ID通过Rest方式传递到Flask接口，然后回传消息的时候将user ID带回来
        //这里一定要异步调用的方式，否则会一直阻塞到所有消息都的到才能跳转到页面
        //异步调用就可以不用等，FLask端口完成所有操作后，再跳转。
//        String url = "http://127.0.0.1:5000/getPredictStatus/" + JwtHelper.getUserId(token)+"/";//异步方式访问端口，进行训练，并通过
//
//        ListenableFuture<ResponseEntity<String>> entity = asyncRestTemplate.getForEntity(url, String.class);
//        entity.addCallback(new SuccessCallback<ResponseEntity<String>>() {
//            @Override
//            public void onSuccess(ResponseEntity<String> result) {
//                log.info("A");
//            }
//        }, new FailureCallback() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.info("B");
//            }
//        });
//        log.info("C");

       return "greeting";
    }
    @RequestMapping("/say")
    public void say() throws IOException {
        webSockTest.sendMessage("sss");
    }
}
