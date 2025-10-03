package com.ChasingtheLight.util;

import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    // 过期时间：2小时（单位：毫秒）
    private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000;
    // 密钥（实际项目中建议放在配置文件，且足够复杂）
    private static final String SECRET_KEY = "a2V5U2l6ZVNlY3JldE1lZGlhTmVlZGVkU2hlbGxUb0JlU2VjdXJlMjU2Qml0";

    // 生成JWT
    public static String generateToken(Integer userId, String userName) {
        // 设置头部信息
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");

        // 生成签名密钥
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");

        // 构建JWT
        return Jwts.builder()
                .setHeader(headers)
                .setSubject(userName)
                .claim("userId", userId) // 确保此处正确传入userId
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 验证JWT并返回载荷信息
    public static Claims verifyToken(String token) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");

            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token已过期");
        } catch (JwtException e) {
            throw new RuntimeException("Token无效");
        }
    }
}