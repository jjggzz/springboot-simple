package com.springboot.simple.support.util;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES加密解密工具
 * @author jgz
 * @version 1.0
 * @date 2020/8/27
 **/
public class AesUtils {

    private static final int KEY_LEN = 16;

    private static final String DEFAULT_KEY = "5jTJvXTgFlvEsQ7F";

    /**
     * aes加密算法
     * @param key 长度为16位的key
     * @param src 明码
     * @return {@link String 密码}
     * @author jgz
     * @date 2020/8/27
     */
    public static String encrypt(String key,String src){
        if(StringUtils.isBlank(key) || key.length() != KEY_LEN){
            throw new RuntimeException("aes加密:key为空或key长度不为16位");
        }
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec aes = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,aes);
            byte[] bytes = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));
            return new BASE64Encoder().encodeBuffer(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("aes加密:加密失败");
        }
    }

    /**
     * aes解密算法
     * @param key 长度为16位的key
     * @param src 密码
     * @return {@link String 明码}
     * @author jgz
     * @date 2020/8/27
     */
    public static String decrypt(String key,String src){
        if(StringUtils.isBlank(key) || key.length() != KEY_LEN){
            throw new RuntimeException("aes解密:key为空或key长度不为16位");
        }
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec aes = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,aes);
            byte[] bytes = new BASE64Decoder().decodeBuffer(src);
            byte[] target = cipher.doFinal(bytes);
            return new String(target,StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("aes解密:解密失败");
        }
    }


}
