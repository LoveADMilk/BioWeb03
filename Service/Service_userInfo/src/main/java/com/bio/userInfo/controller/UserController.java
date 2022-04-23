package com.bio.userInfo.controller;

import com.bio.common.helper.JwtHelper;
import com.bio.common.util.IDUtils;
import com.bio.entityModel.model.user.userInfo;
import com.bio.userInfo.service.MailService;
import com.bio.userInfo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    /**
     * 用户注册
     * **/
    @RequestMapping("/register")
    public String register(userInfo user){
        user.setActiveStatus(0);//状态
        String activeCode = IDUtils.getUUID();//生成随机激活码
        user.setActiveCode(activeCode);
        /**
         * 这里加入判断是否出现邮箱和名字重复的功能
         * */
        Integer userId = userService.selectUserIdByMail(user.getEmail());
        //如果是空的话就是Null也就是数据库中无重复
        if(userId != null){
            log.info("当前的邮箱重复");
            return "index";
        }
        log.info("用户ID为："+ userId);
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

    //直接访问到登录界面
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    /**
     * 登录功能
     * @param  email, password 传入的登录用户的邮箱与密码
     * **/
    //点击登录按钮之后
    @RequestMapping("/userLogin")
    public String userLogin(Model model, String email, String password, HttpServletResponse response){
        //返回用户对象根据邮箱
        userInfo user = userService.selectUserByEmail(email);
        String token = "";
        if(user == null || !user.getPassword().equals(password)){
            log.info("用户密码或邮箱错误请重新登录");
        }else{
            log.info(user.getEmail() + "该用户登陆成功");
//            获得token
            token = JwtHelper.createToken(user.getId(),user.getNickName(),user.getEmail());
            //登录成功返回到login
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            //登录之后保持登陆状态，将用户信息转回到前端
            model.addAttribute("userId", JwtHelper.getUserId(token));
            model.addAttribute("userName", JwtHelper.getUserName(token));
            model.addAttribute("userEmail", JwtHelper.getUserEmail(token));
        }

        return "login";
    }

    //登出
    @RequestMapping("/logout")
    public String logout(HttpSession session, SessionStatus sessionStatus){
        session.invalidate();
        sessionStatus.setComplete();
        return "login";
    }

}
