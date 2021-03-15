package com.springboot.simple.controller;

import com.springboot.simple.redis.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JGZ
 * CreateTime 2020/3/26 21:42
 */
@Api(value = "desc of class")
@RestController
public class UserController extends BaseController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "desc of method", notes = "")
    @GetMapping("getget")
    public void get(){
        BoundZSetOperations<String, String> test = redisTemplate.boundZSetOps("TEST");
        String match = "*-1-*";
        Cursor<ZSetOperations.TypedTuple<String>> scan = test.scan(ScanOptions.scanOptions().match(match).build());
        while (scan.hasNext()) {
            System.out.println(scan.next().getValue());
        }
    }

    @GetMapping("setset")
    public void set(){
        BoundZSetOperations<String, String> test = redisTemplate.boundZSetOps("TEST");
        test.add("aa-1-#1",10);
        test.add("bb-1-#1",20);
        test.add("cc-1-#1",30);
        test.add("dd-1-#2",40);
        test.add("ee-1-#1",50);
        test.add("ff-1-#2",60);
        test.add("gg-1-#1",70);
    }


}
