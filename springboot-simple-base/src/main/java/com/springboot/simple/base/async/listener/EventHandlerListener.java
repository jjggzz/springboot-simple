package com.springboot.simple.base.async.listener;

import com.springboot.simple.base.async.interfaces.BaseHandlerInterface;
import com.springboot.simple.base.async.event.BaseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author jgz
 * CreateTime 2020/8/22 9:38
 */
@EnableAsync
@Component
public class EventHandlerListener {

    @Async
    @EventListener
    public void handler(BaseEvent baseEvent){
        BaseHandlerInterface source = (BaseHandlerInterface) baseEvent.getSource();
        source.handler();
    }

}
