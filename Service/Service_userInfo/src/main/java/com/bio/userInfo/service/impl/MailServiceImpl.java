package com.bio.userInfo.service.impl;

import com.bio.entityModel.model.user.userInfo;
import com.bio.userInfo.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendMail(userInfo user) {
        String code = user.getActiveCode();//激活码

        log.info("注册的激活码"+ code);

        String subject = "来自生物信息平台的网站的验证登录(10分钟后过期)";

        //上面的激活码发送到用户注册邮箱
        String content = "<a href=\"http://localhost:8150/checkCode?code="+code+"\">激活请点击:"+code+"</a>";

        String to = user.getEmail();
        //发送激活邮件
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(content, true);
            javaMailSender.send(message);
            //日志信息
            log.info("发送邮件成功--from:"+ from +" to:"+ to);
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }
    }

}
