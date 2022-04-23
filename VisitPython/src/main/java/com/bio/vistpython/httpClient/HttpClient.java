package com.bio.vistpython.httpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpClient {
//    String url 请求网址， HttpMethod 请求方式 ，MultiValueMap 返回结果
    //利用RestTemplate方式访问接口
    public String client(String url, HttpMethod method, MultiValueMap<String,String> params){
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.getForEntity(url,
                String.class);
        return responseEntity.getBody();
    }

}
