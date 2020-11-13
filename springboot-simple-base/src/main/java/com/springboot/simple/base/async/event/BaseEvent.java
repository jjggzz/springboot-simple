package com.springboot.simple.base.async.event;

import com.springboot.simple.base.async.interfaces.BaseHandlerInterface;
import org.springframework.context.ApplicationEvent;

/**
 * 预定义的事件
 * @author jgz
 * CreateTime 2020/8/22 9:43
 */
public class BaseEvent extends ApplicationEvent {

    public BaseEvent(BaseHandlerInterface baseHandlerInterface) {
        super(baseHandlerInterface);
    }
}
