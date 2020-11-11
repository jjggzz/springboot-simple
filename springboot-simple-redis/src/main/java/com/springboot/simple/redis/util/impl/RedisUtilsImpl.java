package com.springboot.simple.redis.util.impl;

import com.springboot.simple.redis.util.RedisUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * 如果容器中有redisUtils的bean(用户自己实现)则不将其添加到容器中
 * @author jgz
 * CreateTime 2020/4/22 21:23
 */
public class RedisUtilsImpl implements RedisUtils {

    private RedisTemplate<String,String> redisTemplate;

    public RedisUtilsImpl(RedisTemplate<String,String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return 操作结果 true成功 false失败
     */
    @Override
    public Boolean expire(String key, long time){
        if (time > 0 && key != null){
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     * @param key 不能为空
     * @return 返回0代表永久有效
     */
    @Override
    public Long getExpire(String key) {
        return key == null ? null : redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true存在 false不存在
     */
    @Override
    public Boolean existKey(String key){
        return key == null ? null : redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     * @param key 可以传一个值或者多个
     */
    @Override
    public Long del(String... key){
        return redisTemplate.delete(Arrays.asList(key));
    }

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    @Override
    public String get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return 操作结果 成功返回true,失败返回false
     */
    @Override
    public Boolean set(String key, String value){
        if (key != null && value != null){
            redisTemplate.opsForValue().set(key,value);
            return true;
        }
        return  false;
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return 返回true成功,返回false失败
     */
    @Override
    public Boolean set(String key, String value, long time){
        if (key != null && value != null && time > 0) {
            redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 递增,传入负数则递减
     * @param key 键
     * @param delta 递增的值(要加几)
     * @return
     */
    @Override
    public Long incr(String key, long delta){
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 向一张哈希表中设置值
     * @param key 键
     * @param item 哈希表中的键
     * @param value 值
     * @return 成功返回true 失败返回false
     */
    @Override
    public Boolean hSet(String key, String item, String value) {
        if (key != null && item != null && value != null){
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        }
        return false;
    }

    /**
     * 向一张哈希表中设置值
     * @param key 键
     * @param item 哈希表中的键
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return 成功返回true 失败返回false
     */
    @Override
    public Boolean hSet(String key, String item, String value, long time) {
        if (key != null && item != null && value != null && time > 0){
            redisTemplate.opsForHash().put(key, item, value);
            return this.expire(key, time);
        }
        return false;
    }

    /**
     * 获取哈希表中的值
     * @param key 键(not null)
     * @param item 哈希表中的键(not null)
     * @return 值
     */
    @Override
    public String hGet(String key, String item){
        if (key != null && item != null){
            return (String) redisTemplate.opsForHash().get(key, item);
        }
        return null;
    }

    /**
     * 设置key所对应的哈希表
     * @param key 键
     * @param map 哈希表
     * @return 成功返回true 失败返回false
     */
    @Override
    public Boolean hmSet(String key, Map<String, String> map){
        if (key != null && map != null){
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }
        return false;
    }

    /**
     * 设置key所对应的哈希表,并设置时间
     * @param key 键
     * @param map 值
     * @param time 时间(s)
     * @return 成功返回true 失败返回false
     */
    @Override
    public Boolean hmSet(String key, Map<String, String> map, long time) {
        if (key != null && map != null && time > 0){
            redisTemplate.opsForHash().putAll(key, map);
            return this.expire(key, time);
        }
        return false;
    }

    /**
     * 删除哈希表中的值
     * @param key 哈希表所对应的键(not null)
     * @param item 哈希表中的键 (not null)
     */
    @Override
    public Long hDel(String key, String... item) {
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断哈希表中是否有该项的值
     * @param key 哈希表对应的键 (not null)
     * @param item 哈希表中的项 (not null)
     * @return true代表有 false代表无
     */
    @Override
    public Boolean hExistKey(String key, String item) {
        if (key != null && item != null){
            return redisTemplate.opsForHash().hasKey(key, item);
        }
        return  false;
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几
     * @return
     */
    @Override
    public Long hIncr(String key, String item, long by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @Override
    public Long sSet(String key, String... values) {
        if (key != null && values != null){
            return redisTemplate.opsForSet().add(key, values);
        }
        return null;
    }

    /**
     * 将set数据放入缓存,并设置时间
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @Override
    public Long sSet(String key, long time, String... values) {
        if(key != null && values != null && time >0){
            Long count = redisTemplate.opsForSet().add(key, values);
            this.expire(key, time);
            return count;
        }
        return null;
    }

    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    @Override
    public Set<String> sGet(String key) {
        return key == null ? null : redisTemplate.opsForSet().members(key);
    }

    /**
     * 判断set中是否存在该value
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    @Override
    public Boolean sExistValue(String key, String value) {
        if (key != null && value != null){
            return redisTemplate.opsForSet().isMember(key, value);
        }
        return null;
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    @Override
    public Long sSize(String key) {
        return key == null? null : redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除set中值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    @Override
    public Long sDel(String key, String... values) {
        if(key != null && values != null){
            return redisTemplate.opsForSet().remove(key, values);
        }
        return null;
    }

    /**
     * 将值放入list缓存
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public Boolean lSet(String key, String value) {
        if(key != null && value != null){
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        }
        return false;
    }

    /**
     * 将值放入list缓存并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    @Override
    public Boolean lSet(String key, String value, long time) {
        if(key != null && value != null && time > 0){
            redisTemplate.opsForList().rightPush(key, value);
            this.expire(key, time);
            return true;
        }
        return false;
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public Long lSet(String key, List<String> value) {
        if(key != null && value != null){
            return redisTemplate.opsForList().rightPushAll(key, value);
        }
        return null;
    }

    /**
     * 将list放入缓存并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    @Override
    public Long lSet(String key, List<String> value, long time) {
        if(key != null && value != null && time > 0){
            Long count = redisTemplate.opsForList().rightPushAll(key, value);
            this.expire(key,time);
            return count;
        }
        return null;
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    @Override
    public Long lSet(String key, String... value) {
        if(key != null && value != null){
            return redisTemplate.opsForList().rightPushAll(key, value);
        }
        return null;
    }

    /**
     * 将list放入缓存并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    @Override
    public Long lSet(String key, long time, String... value) {
        if(key != null && value != null && time > 0){
            Long count = redisTemplate.opsForList().rightPushAll(key, value);
            this.expire(key,time);
            return count;
        }
        return null;
    }

    /**
     * 获取list缓存的内容(0 到 -1代表所有值)
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return 获取的结果
     */
    @Override
    public List<String> lGet(String key, long start, long end) {
        return key == null? null : redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    @Override
    public String lGet(String key, long index) {
        return key == null ? null : redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    @Override
    public Long lSize(String key) {
        return key == null ? null : redisTemplate.opsForList().size(key);
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @param value 值
     * @return
     */
    @Override
    public Boolean lUpdateIndex(String key, long index, String value) {
        if(key != null && value != null){
            redisTemplate.opsForList().set(key, index, value);
            return true;
        }
        return false;
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    @Override
    public Long lDel(String key, String value, long count) {
        if(key != null && value != null && count > 0){
            return redisTemplate.opsForList().remove(key, count, value);
        }
        return null;
    }


}





