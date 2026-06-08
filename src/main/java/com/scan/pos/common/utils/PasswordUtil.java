package com.scan.pos.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 密码加密工具类
 * 使用 SHA-256 + 随机盐值 进行单向哈希，存储格式为 hex(salt):hex(hash)
 */
public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16; // 16 bytes = 128 bits
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    /**
     * 对明文密码进行加密
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码，格式：hex(salt):hex(hash)
     */
    public static String encode(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        byte[] hash = digest(salt, rawPassword);
        return bytesToHex(salt) + ":" + bytesToHex(hash);
    }

    /**
     * 校验明文密码是否与加密密码匹配
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 加密后的密码（encode 方法返回的格式）
     * @return true 匹配，false 不匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        String[] parts = encodedPassword.split(":", 2);
        if (parts.length != 2) {
            return false;
        }
        byte[] salt;
        byte[] expectedHash;
        try {
            salt = hexToBytes(parts[0]);
            expectedHash = hexToBytes(parts[1]);
        } catch (IllegalArgumentException e) {
            return false;
        }
        byte[] actualHash = digest(salt, rawPassword);
        return MessageDigest.isEqual(expectedHash, actualHash);
    }

    private static byte[] digest(byte[] salt, String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            md.update(rawPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hex = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hex[i * 2] = HEX_CHARS[v >>> 4];
            hex[i * 2 + 1] = HEX_CHARS[v & 0x0F];
        }
        return new String(hex);
    }

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string length");
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
