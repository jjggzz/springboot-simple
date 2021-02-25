package com.springboot.simple.controller;

import com.springboot.simple.base.fun.ICustomer;
import com.springboot.simple.base.fun.IFunction;
import com.springboot.simple.res.ResultEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author jgz
 * CreateTime 2020/4/23 11:26
 */
public abstract class BaseController {

    protected HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    protected HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 处理业务并返回数据
     * @param param
     * @param callback
     * @param <T>
     * @param <E>
     * @return
     * @throws Exception
     */
    protected <T,E> ResultEntity<E> result(T param, IFunction<T,ResultEntity<E>> callback) throws Exception {
        return callback.apply(param);
    }

    /**
     * 处理业务不返回数据
     * @param param
     * @param callback
     * @param <T>
     * @throws Exception
     */
    protected <T> void noResult(T param, ICustomer<T> callback) throws Exception {
        callback.accept(param);
    }

}
