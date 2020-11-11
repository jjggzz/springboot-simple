package com.springboot.simple.exception;

import com.springboot.simple.res.ResultEntity;
import com.springboot.simple.res.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 全局异常处理
 * @author jgz
 * CreateTime 2020/4/23 11:01
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 全局异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultEntity<?> globalException(Exception e){
        //业务异常则抛出自定义的异常信息
        if(e instanceof GlobalException){
            return new ResultEntity<>(((GlobalException) e).getStatus(),((GlobalException) e).getCode(),e.getMessage());
        }
        logger.error(e.getMessage());
        return new ResultEntity<>(ResultEnum.ERROR);
    }

}
