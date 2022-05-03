package com.bio.common.util;

import com.bio.common.helper.JwtHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class GetUserVo {
    public static Map<String, Object> getUserInfo(HttpServletRequest request){

        Cookie[] cookies =  request.getCookies();
        String token = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("Biotoken")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("Biotoken", token);
        map.put("userName", JwtHelper.getUserName(token));
        map.put("userId", JwtHelper.getUserId(token));
        map.put("userEmail", JwtHelper.getUserEmail(token));

        return map;
    }
}
