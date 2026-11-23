package com.atlei;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    // 生成令牌
    @Test
    public void testGenerateJwt() {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "YXRsZWk="/*atlei*/)
                .addClaims(dataMap)//添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))//设置过期时间
                .compact();
        System.out.println(jwt);
    }

    //解析JWT令牌
    @Test
    public void testParseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc5MDc2MTgzMH0.Ujrln018UuPLxJ5qcv9Arp5-vezOJWugXIYYTR1eUhQ";
        Claims body = Jwts.parser().setSigningKey("YXRsZWk=").parseClaimsJws(token).getBody();
        System.out.println(body);
    }
}
