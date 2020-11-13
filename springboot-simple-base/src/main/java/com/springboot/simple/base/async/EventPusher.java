package com.springboot.simple.base.async;

import com.springboot.simple.base.async.event.BaseEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 基础的事件发布者,通过他发我我们定义好的事件
 * @author jgz
 * CreateTime 2020/8/22 9:34
 */
@Component
public class EventPusher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void eventPush(BaseEvent baseEvent){
        applicationContext.publishEvent(baseEvent);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
