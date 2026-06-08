package com.scan.pos.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密工具类
 * <p>
 * 使用 BCrypt 进行密码哈希（行业标准，抗暴力破解）。
 * BCrypt 内置盐值 + 自适应 cost factor（默认10），无需手动管理盐值。
 * </p>
 */
public class PasswordUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 对明文密码进行 BCrypt 加密
     *
     * @param rawPassword 明文密码
     * @return BCrypt 哈希（格式：$2a$10$...）
     */
    public static String encode(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        return ENCODER.encode(rawPassword);
    }

    /**
     * 校验明文密码是否与 BCrypt 哈希匹配
     *
     * @param rawPassword     明文密码
     * @param encodedPassword BCrypt 哈希
     * @return true 匹配，false 不匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
