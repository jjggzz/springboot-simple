package com.springboot.simple.exception;

import com.springboot.simple.res.ResultEntity;
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
     * @return ResultEntity
     */
    @ExceptionHandler(Exception.class)
    public ResultEntity<?> globalException(Exception e){
        //业务异常则抛出自定义的异常信息
        if(e instanceof BusinessException){
            return ResultEntity.failure(((BusinessException) e).getCode(), e.getMessage());
        }
        logger.error(e.getMessage());
        return ResultEntity.failure("系统正在忙");
    }

}
