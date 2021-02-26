package com.springboot.simple.redis.config;

import com.springboot.simple.redis.util.RedisUtils;
import com.springboot.simple.redis.util.impl.RedisUtilsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 * @author jgz
 * CreateTime 2020/4/22 21:15
 */
@Configuration
public class RedisConfiguration {

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * 实例化 redisTemplate
     * @param factory RedisConnectionFactory
     * @return 实例化对象
     */
    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory factory){
        LOGGER.debug("redis init start...");
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        LOGGER.debug("redis init success...");
        return redisTemplate;
    }

    @ConditionalOnMissingBean(RedisUtils.class)
    @Bean
    public RedisUtils redisUtils(RedisTemplate<String,String> redisTemplate){
        return new RedisUtilsImpl(redisTemplate);
    }
}
