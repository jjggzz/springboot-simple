package com.springboot.simple.async;

import com.springboot.simple.base.async.interfaces.BaseHandlerInterface;

import java.util.Date;

/**
 * 异步事件测试
 *
 * @author jgz
 * CreateTime 2020/8/22 9:58
 */
public class DemoHandler implements BaseHandlerInterface {

    private String name;

    private Integer age;

    public DemoHandler(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void handler() {
        System.out.println(new Date() + ":" + name + ":" + age);
    }
}
