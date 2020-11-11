package com.springboot.simple.jdbc.annotation;

import java.lang.annotation.*;

/**
 * @author JGZ
 * CreateTime 2020/3/29 9:47
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "master";
}
