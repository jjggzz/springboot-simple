package com.springboot.simple.controller;

import com.springboot.simple.async.DemoHandler;
import com.springboot.simple.base.async.EventPusher;
import com.springboot.simple.base.async.event.BaseEvent;
import com.springboot.simple.model.UserModel;
import com.springboot.simple.res.ResultEntity;
import com.springboot.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author JGZ
 * CreateTime 2020/3/26 21:42
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private RedisUtils redisUtils;

    @Autowired
    private EventPusher eventPusher;

    @GetMapping("/user")
    public void add(){
        UserModel userModel = new UserModel();
        userModel.setName("zhangs");
        userModel.setAge(18);
        userModel.setSex("男");
        userService.save(userModel);
    }

    @GetMapping("user/{id}")
    public ResultEntity<?> get(@PathVariable("id") Long id){
        HttpServletRequest request = getRequest();
        return ResultEntity.success(userService.getById(id));
    }

    @GetMapping("user/count")
    public Integer count(){
        return userService.countUser();
    }

//    @GetMapping("show")
//    public void show(){
////        String k1 = redisUtils.get("k1");
//        System.out.println(redisUtils);
////        redisUtils.get("k1");
////        redisUtils.set("k1","v1");
//        String k1 = redisUtils.get("k1");
//        System.out.println(k1);
//    }

    @GetMapping("testAsync")
    public void testAsync(){
        try {
            eventPusher.eventPush(new BaseEvent(new DemoHandler("张三",18)));
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
