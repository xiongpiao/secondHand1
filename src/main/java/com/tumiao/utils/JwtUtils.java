package com.tumiao.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class JwtUtils {

    private static long time = 1000*60*60*24*3;
    private static String signature = "admin";//自定义一个签名信息
    //编码
    public static String encode(String username,String password)
    {
        //创建jwt对象
        JwtBuilder jwtBuilder = Jwts.builder();//builder就是用来构建jwt的对象的
        String token = jwtBuilder
                    //设置第一部分：header，该部分用于存放类型和二次编码的算法名称
                    .setHeaderParam("typ","JWT")//类型
                    .setHeaderParam("alg","HS256")//算法名称
                    //设置第二部分：payload,该部分用于存放有效信息
                    .claim("username",username)
                    .claim("password",password)
                    .setExpiration(new Date(System.currentTimeMillis()+time))//设置有效时间：这里设置的是三天
                    .setId(UUID.randomUUID().toString())//设置id
                    //设置第三部分：signature
                    .signWith(SignatureAlgorithm.HS256,signature)//设置编码算法和签名值
                    .compact();//最后将这三部分进行拼接
        return token;
    }
    //解码
    public static Map<String,String> parse(String token)
    {
        String username = "";
        String password = "";
        JwtParser jwtParser = Jwts.parser();//创建解密对象
        try {
            Jws<Claims> claimsJws = jwtParser
                    .setSigningKey(signature) //根据签名进行解密
                    .parseClaimsJws(token);//解析token得到存放的有效信息
            Claims claims = claimsJws.getBody();//通过该对象，可以得到存放在第二部分的所有信息，包括有效时间，id等
            username = (String) claims.get("username");
            password = (String) claims.get("password");
//            System.out.println(username);
//            System.out.println(password);
        }catch (Exception e)
        {
            return null;
        }
        Map<String,String> info = new HashMap<>();
        info.put("username",username);
        info.put("password",password);
        return info;
    }
}
