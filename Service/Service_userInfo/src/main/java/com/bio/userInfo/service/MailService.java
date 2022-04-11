package com.bio.userInfo.service;

import com.bio.entityModel.model.user.userInfo;

public interface MailService {
    /**
     *  发送多媒体类型邮件
     */
//    void sendMimeMail(String to, String subject, String content);//发送邮件

    void sendMail(userInfo user);
}
