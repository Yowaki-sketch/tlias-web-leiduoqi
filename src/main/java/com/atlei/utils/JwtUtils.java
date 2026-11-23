package com.atlei.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // 签名密钥（自定义混淆字符串）
    private static final String SIGN_KEY = "YXRsZWlfd2ViX21hbmFnZW1lbnRfcHJvamVjdF9rZXkxMjM=";
    // 默认过期时间：12 小时（单位：毫秒）
    private static final Long EXPIRE_TIME = 12 * 3600 * 1000L;

    /**
     * 生成 JWT 令牌
     *
     * @param claims 存入载荷（Payload）的自定义业务数据（例如：id、username）
     * @return 生成的 JWT 字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .compact();
    }

    /**
     * 解析 JWT 令牌
     *
     * @param token 传入的令牌字符串
     * @return Claims 载荷数据（可从中取出 id、username 等）
     * @throws io.jsonwebtoken.JwtException 令牌过期、被篡改或格式错误时抛出异常
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGN_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}
