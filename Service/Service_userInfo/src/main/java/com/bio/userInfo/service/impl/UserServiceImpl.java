package com.bio.userInfo.service.impl;

import com.bio.entityModel.model.user.userInfo;
import com.bio.userInfo.mapper.UserMapper;
import com.bio.userInfo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void add(userInfo user) {
        //在进入数据库之前可以先存入到Redis中，等用户完成认证之后再存进数据库

        userMapper.insertUserInfo(user);

        String code = user.getActiveCode();//激活码

        log.info("注册的激活码"+ code);

        String subject = "来自生物信息平台的网站";

        //上面的激活码发送到用户注册邮箱
        String context = "<a href=\"http://localhost:8080/user/checkCode?code="+code+"\">激活请点击:"+code+"</a>";

        //发送激活邮件

    }
}
