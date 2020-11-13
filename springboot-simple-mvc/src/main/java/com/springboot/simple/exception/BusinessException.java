package com.springboot.simple.exception;

import com.springboot.simple.res.BaseResultEnum;

/**
 * @author jgz
 * CreateTime 2020/4/23 11:26
 */
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(Integer code,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(BaseResultEnum baseResultEnum) {
        super(baseResultEnum.getMessage());
        this.code = baseResultEnum.getCode();
        this.message = baseResultEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
