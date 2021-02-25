package com.springboot.simple.res;

import com.springboot.simple.exception.BusinessExceptionAssert;

public enum BaseResultEnum implements BusinessExceptionAssert {
    SUCCESS(200,"操作成功"),
    FAILURE(500,"系统正在忙");

    BaseResultEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;
    private final String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
