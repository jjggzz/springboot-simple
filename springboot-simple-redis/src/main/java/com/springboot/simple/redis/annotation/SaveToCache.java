package com.springboot.simple.redis.annotation;

import java.lang.annotation.*;

/**
 * 保存到redis的注解
 * @author jgz
 * CreateTime 2020/5/14 16:54
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaveToCache {
    long time() default -1;
}
