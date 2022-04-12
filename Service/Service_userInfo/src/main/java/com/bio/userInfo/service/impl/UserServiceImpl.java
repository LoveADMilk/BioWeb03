package com.bio.userInfo.service.impl;

import com.bio.entityModel.model.user.userInfo;
import com.bio.userInfo.mapper.UserMapper;
import com.bio.userInfo.service.MailService;
import com.bio.userInfo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    MailService mailService;

    @Override
    public void insertUser(userInfo user) {
        userMapper.insertUserInfo(user);
        log.info("用户信息注入数据库成功");
    }

    @Override
    public Integer selectUserIdByMail(String email) {
        return userMapper.selectUserIdByMail(email);
    }

    @Override
    public userInfo selectUserByEmail(String email) {
        return userMapper.selectUserByMail(email);
    }

}
