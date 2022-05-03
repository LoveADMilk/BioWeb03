package com.bio.vistpython.controller;

import com.bio.common.helper.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.AsyncRestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class GetResults {

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;//自动注入的时候一定要加入泛型

    // 点击这里实现预测功能。。。
    @RequestMapping("/getResults")
    public String greet(Model model,
                        HttpServletRequest request,
                        @ModelAttribute(value = "virusSequence1") String virusSequence1,
                        @ModelAttribute(value = "virusSequence2") String virusSequence2,
                        @ModelAttribute(value = "modelId") Integer modelId,
                        @ModelAttribute(value = "encodeId") Integer encodeId
                        ){
        //序列存入Redis中
        List<String>  sequenceList = new ArrayList<>();
        sequenceList.add(virusSequence1);
        sequenceList.add(virusSequence2);

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

        //将EncodeId,userId,modelId\组合成为唯一RedistId,将两个序列存入列表作为value
        String key = JwtHelper.getUserId(token) + "" + modelId + "" + encodeId;
        System.out.println("key: " + key);
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPushAll(key, sequenceList);//存入Redis

        //将用户的ID通过Rest方式传递到Flask接口，然后回传消息的时候将user ID带回来
        //这里一定要异步调用的方式，否则会一直阻塞到所有消息都的到才能跳转到页面
//        异步调用就可以不用等，FLask端口完成所有操作后，再跳转。
        String url = "http://127.0.0.1:5000/getPredictStatus/" + JwtHelper.getUserId(token)+"/" +modelId + "/" + encodeId + "/";//异步方式访问端口，进行训练，并通过

        ListenableFuture<ResponseEntity<String>> entity = asyncRestTemplate.getForEntity(url, String.class);
        entity.addCallback(new SuccessCallback<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String> result) {
                log.info("成功返回");
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("失败返回");
            }
        });
        log.info("C");

        return "getPredictionResults";
    }

}
