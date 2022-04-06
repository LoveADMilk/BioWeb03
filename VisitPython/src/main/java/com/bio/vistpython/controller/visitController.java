package com.bio.vistpython.controller;

import com.bio.vistpython.httpClient.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class visitController {
    @Autowired
    HttpClient httpClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/hello")
    public String getFromFlask(){
        String url = "http://127.0.0.1:5000";//Flask启动的默认端口
        HttpMethod httpMethod = HttpMethod.GET;//Flask 在开启端口的时候默认是get访问
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        return httpClient.client(url, httpMethod, params);
    }
}
