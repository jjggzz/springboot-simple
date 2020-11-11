package com.springboot.simple.support.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author jgz
 * @date 2020/8/26
 * @desc 随机生成工具
 **/
public class RandomUtils {

    private static final char[] CHARS = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    /**
     * 随机生成字符串
     * @param len 长度
     * @return {@link String}
     * @author jgz
     * @date 2020/8/26
     */
    public static String randomStr(int len){
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int r = ThreadLocalRandom.current().nextInt(CHARS.length);
            stringBuilder.append(CHARS[r]);
        }
        return stringBuilder.toString();
    }

    /**
     * 随机生成一个只包含数字的字符串(可能会以0开头)
     * @param len 长度
     * @return {@link String}
     * @author jgz
     * @date 2020/8/27
     */
    public static String randomInt(int len){
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int r = ThreadLocalRandom.current().nextInt(10);
            stringBuilder.append(CHARS[r]);
        }
        return stringBuilder.toString();
    }

    /**
     * 随机生成只包含字母的字符串
     * @param len 长度
     * @return {@link String}
     * @author jgz
     * @date 2020/8/27
     */
    public static String randomLetter(int len){
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int r = ThreadLocalRandom.current().nextInt(52);
            stringBuilder.append(CHARS[r + 10]);
        }
        return stringBuilder.toString();
    }

    /**
     * 随机生成只包含大写字母的字符串
     * @param len 长度
     * @return {@link String}
     * @author jgz
     * @date 2020/8/27
     */
    public static String randomUpperLetter(int len){
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int r = ThreadLocalRandom.current().nextInt(26);
            stringBuilder.append(CHARS[r + 10]);
        }
        return stringBuilder.toString();
    }

    /**
     * 随机生成只包含小写字母的字符串
     * @param len 长度
     * @return {@link String}
     * @author jgz
     * @date 2020/8/27
     */
    public static String randomLowerLetter(int len){
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int r = ThreadLocalRandom.current().nextInt(26);
            stringBuilder.append(CHARS[r + 36]);
        }
        return stringBuilder.toString();
    }

}
