package com.zjh.backend.security_;


import com.zjh.backend.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtProvider {
    private static final String SECRET_KEY = "wakanda_forever";
    private static final long EXPIRATION_TIME = 86400000; // 24h

    // 生成jwt所需的map结构
    public static Map<String, Object> generateChaims(User user){
        // 将用户账号，id，用户权限放入到JWT中
        HashMap<String, Object> chaims = new HashMap<>();
        chaims.put("id", user.getId());
        chaims.put("userAccount", user.getUserAccount());
        chaims.put("userRole", user.getUserRole());
        return chaims;
    }

    // Authentication 是什么东西 一个元组
    // 生成jwt信息
    public static String generateJWToken(User user){

        Map<String, Object> chaims = generateChaims(user);
        return Jwts.builder() // --> 生成jwt令牌
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setClaims(chaims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    // 解析JWT
    public static User parseJWT(HttpServletRequest request){
        String jwt = request.getHeader("token");
        Claims body = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
        User user = new User();
        user.setId((Long) body.get("id"));
        user.setUserAccount((String) body.get("userAccount"));
        user.setUserRole((Integer) body.get("userRole"));
        return user;

    }
}
