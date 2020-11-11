package com.springboot.simple.redis.aop;

import com.alibaba.fastjson.JSON;
import com.springboot.simple.redis.annotation.SaveToCache;
import com.springboot.simple.redis.util.RedisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * redis注解缓存切面实现
 * @author jgz
 * CreateTime 2020/5/14 17:01
 */
@Aspect
@Component
public class CacheAspect {

    private RedisUtils redisUtils;

    public CacheAspect(RedisUtils redisUtils){
        this.redisUtils = redisUtils;
    }

    @Pointcut("@annotation(com.springboot.simple.redis.annotation.SaveToCache)")
    public void annotationPointcut() {
    }

    /**
     * TODO
     * redis缓存key的设置暂时未想到好的设置方法
     * @param proceedingJoinPoint
     * @return {@link Object}
     * @author jgz
     * @date 2020/5/25 17:58
     */
    @Around("annotationPointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Object proceed = null;
        try {
            //获取参数
            Object[] args = proceedingJoinPoint.getArgs();
            //获取返回值
            proceed = proceedingJoinPoint.proceed(args);
            if(proceed != null){
                //获取保存时间
                MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
                Method method = signature.getMethod();
                SaveToCache annotation = method.getAnnotation(SaveToCache.class);
                long time = annotation.time();
                if(time <= 0){
                    //设为永久
                    redisUtils.set(proceed.getClass().getName() + "-" + toJson(args[0]),toJson(proceed));
                }
                else{
                    //设为对应时间
                    redisUtils.set(proceed.getClass().getName() + "-" + toJson(args[0]),toJson(proceed),time);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

    private String toJson(Object o){
        return JSON.toJSONString(o);
    }

}
