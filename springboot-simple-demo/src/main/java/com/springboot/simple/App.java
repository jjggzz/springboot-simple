package com.springboot.simple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author JGZ
 * CreateTime 2020/3/26 21:42
 */
@MapperScan(basePackages = "com.springboot.simple.mapper")
@SpringBootApplication(scanBasePackages = "com.springboot.simple")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
