package com.bio.common.helper;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {

    //过期时间
    private static long tokenExpiration = 60 * 60 * 24 * 90;
    //签名秘钥
    private static String tokenSignKey = "123456";

    //根据参数生成token
    public static String createToken(Integer userId, String userName, String email) {
        String token = Jwts.builder()
                .setSubject("USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))//过期时间设置
                .claim("userId", userId)//
                .claim("userName", userName)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //根据token字符串得到用户id
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Integer userId = null;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            userId = (Integer)claims.get("userId");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT过期");
            return null;
        }
        return userId.longValue();
    }

    //根据token字符串得到用户名称
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Claims claims;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            claims = claimsJws.getBody();
        }catch (ExpiredJwtException e){
            System.out.println("JWT过期");
            return "";

        }
        return (String)claims.get("userName");
    }
    //根据token字符串得到用户emaol
    public static String getUserEmail(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Claims claims;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            claims = claimsJws.getBody();
        }catch (ExpiredJwtException e){
            System.out.println("JWT过期");
            return "";
        }
        return (String)claims.get("email");
    }

//    public static void main(String[] args) {
//        String token = JwtHelper.createToken(1L, "lucy");
//        System.out.println(token);
//        System.out.println(JwtHelper.getUserId(token));
//        System.out.println(JwtHelper.getUserName(token));
//    }
}

