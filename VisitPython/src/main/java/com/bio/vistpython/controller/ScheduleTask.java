package com.bio.vistpython.controller;

import com.bio.vistpython.httpClient.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class ScheduleTask {
    @Autowired
    private HttpClient httpClient;
    /**
     *
     * 爬取论文定时任务：
     * **/
//    cron="*/5 * * * * ?" 利用每五秒访问进行测试
    @Scheduled(cron="*/5 * * * * ?")
    public void collectPapers(){
        System.out.println("访问了一次");
        String url = "http://127.0.0.1:5000/collectPapers";//Flask启动的默认端口
        HttpMethod httpMethod = HttpMethod.GET;//Flask 在开启端口的时候默认是get访问
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        httpClient.client(url, httpMethod, params);
    }
}
