package com.bio.userInfo.controller;

import com.bio.common.util.IDUtils;
import com.bio.entityModel.model.user.userInfo;
import com.bio.userInfo.service.MailService;
import com.bio.userInfo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @Resource
//  想要获取RedisTemplate< String, Object>的Bean，要根据名字装配。
    private RedisTemplate<String , Object> redisTemplate;

    //临时的index控制器
    @GetMapping ("/")
    public String index(){
        return "index";
    }

//  登录板块
    @RequestMapping("/register")
    public String register(userInfo user){
        user.setActiveStatus(0);//状态
        String activeCode = IDUtils.getUUID();//生成随机激活码
        user.setActiveCode(activeCode);
        //先存入Redis中 key为activeCode ， value：为对象
        redisTemplate.opsForValue().set(activeCode, user, 60*10, TimeUnit.SECONDS);//设这10分钟的过期时间
        //发送邮件
        mailService.sendMail(user);
        return "success";//返回成功界面
    }

    @RequestMapping("/checkCode")
    public String checkCode(@RequestParam(value = "code", required = true) String code){
        log.info("传递进的激活码： " + code);
        if(code.equals("")) {
            return "error";
        }
        if (!redisTemplate.hasKey(code)){
            log.info("验证时间过期，请重新注册");
            //通知用户重新注册
        }
        userInfo user = (userInfo) redisTemplate.opsForValue().get(code);
        System.out.println(user);//输出序列化返回之后的用户信息
//        如果用户为不空，就存入到数据库中
        if (user != null){
            user.setActiveStatus(1);//修改状态
            user.setActiveCode("");//将激活码设为空
            //存入数据库
            userService.insertUser(user);
        }
        //验证成功后返回登录界面
        return "login";
    }
}
