package com.bio.userInfo.controller;

import com.bio.common.util.IDUtils;
import com.bio.entityModel.model.user.userInfo;
import com.bio.userInfo.mapper.UserMapper;
import com.bio.userInfo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
//@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/usertest")
    public String getUser(){

        return "OK";
    }

    //临时的index控制器
    @GetMapping ("/")
    public String index(){
        return "index";
    }

//    登录板块
    @RequestMapping("/register")
    public String register(userInfo user){
        user.setActiveStatus(0);//状态
        String activeCode = IDUtils.getUUID();//生成随机激活码
        user.setActiveCode(activeCode);
        userService.add(user); //存入数据库
        return "success";//返回成功界面

    }
}
