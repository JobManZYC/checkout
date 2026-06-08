package com.scan.pos.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类
 * <p>
 * 生成和验证 JWT Token，用于登录后的身份认证。
 * Token 包含用户ID、用户名、角色、商户ID等信息，过期时间 24 小时。
 * </p>
 */
public class JwtUtil {

    /** Token 过期时间：24 小时（毫秒） */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000L;

    /** JWT 签名密钥（>= 256 bits for HS256） */
    private static final String SECRET = "PosSystemJwtSecretKey2024!@#$%^&*()VeryLongSecureKey32Bytes+";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成 JWT Token
     *
     * @param userId     用户ID
     * @param username   用户名
     * @param role       角色：0-超级管理员 1-商户管理员 2-普通销售
     * @param merchantId 商户ID
     * @return JWT Token 字符串
     */
    public static String generateToken(Long userId, String username, Integer role, Long merchantId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRE_TIME);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("userId", userId)
                .claim("username", username)
                .claim("role", role)
                .claim("merchantId", merchantId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析并验证 JWT Token
     *
     * @param token JWT Token 字符串
     * @return Token 中的 Claims（验证失败返回 null）
     */
    public static Claims parseToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * 从 Token 中获取用户ID
     */
    public static Long getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) return null;
        Object userId = claims.get("userId");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }
        return (Long) userId;
    }

    /**
     * 从 Token 中获取用户名
     */
    public static String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims != null ? (String) claims.get("username") : null;
    }

    /**
     * 从 Token 中获取角色
     */
    public static Integer getRole(String token) {
        Claims claims = parseToken(token);
        if (claims == null) return null;
        return (Integer) claims.get("role");
    }

    /**
     * 从 Token 中获取商户ID
     */
    public static Long getMerchantId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) return null;
        Object merchantId = claims.get("merchantId");
        if (merchantId instanceof Integer) {
            return ((Integer) merchantId).longValue();
        }
        return (Long) merchantId;
    }

    /**
     * 验证 Token 是否有效
     */
    public static boolean validateToken(String token) {
        return parseToken(token) != null;
    }
}
