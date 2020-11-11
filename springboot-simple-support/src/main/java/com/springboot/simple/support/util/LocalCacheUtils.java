package com.springboot.simple.support.util;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存实现
 * @author jgz
 * @version 1.0
 * @date 2020/6/5 18:19
 **/
public class LocalCacheUtils {
    private static Map<String,Data> cache = new ConcurrentHashMap<>();

    private static class Data {
        private String key;
        private Object value;
        private long timeout;

        public Data() {
        }

        public Data(String key, Object value, long timeout) {
            this.key = key;
            this.value = value;
            this.timeout = timeout;
        }

        public Data(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }
    }


    /**
     * 设置缓存,过期时间为Long.MAX_VALUE,成功返回true
     * @param key 键
     * @param value 值
     * @return {@link boolean}
     * @author jgz
     * @date 17:28 2020/7/8
     */
    public static boolean set(String key,Object value){
        if(key == null || key.trim().length() == 0){
            return false;
        }
        if(value == null){
            return false;
        }
        clearTimeoutCache();
        cache.put(key,new Data(key,value,Long.MAX_VALUE));
        return true;
    }

    /**
     * 设置缓存,并为缓存设置过期时间,成功返回true
     * @param key 键
     * @param value 值
     * @param timeout 过期时长(毫秒)
     * @return {@link boolean}
     * @author jgz
     * @date 17:29 2020/7/8
     */
    public static boolean set(String key,Object value,long timeout){
        if(key == null || key.trim().length() == 0){
            return false;
        }
        if(value == null){
            return false;
        }
        clearTimeoutCache();
        cache.put(key,new Data(key,value,Calendar.getInstance().getTimeInMillis() + timeout));
        return true;
    }

    /**
     * 获取键所对应的值,不存在则返回空
     * @param key 键
     * @return {@link java.lang.Object}
     * @author jgz
     * @date 17:31 2020/7/8
     */
    public static Object get(String key){
        if (key == null || key.trim().length() == 0) {
            return null;
        }
        clearTimeoutCache();
        Data data = cache.get(key);
        return data == null ? null : data.value;
    }

    /**
     * 移除缓存,成功返回true
     * @param key 键
     * @return {@link boolean}
     * @author jgz
     * @date 17:32 2020/7/8
     */
    public static boolean remove(String key){
        if (key == null || key.trim().length() == 0) {
            return false;
        }
        cache.remove(key);
        return true;
    }

    /**
     * 过期键值对清理
     * @author jgz
     * @date 17:49 2020/7/8
     */
    private static void clearTimeoutCache(){
        if(!cache.keySet().isEmpty()){
            cache.entrySet().removeIf(item -> item.getValue().getTimeout() <= Calendar.getInstance().getTimeInMillis());
        }
    }


}
