package com.bio.userInfo.service.impl;

import com.bio.userInfo.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    //to:目的
//    subject 标题
//    content 内容
    @Override
    public void sendMimeMail(String to, String subject, String content) {
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
