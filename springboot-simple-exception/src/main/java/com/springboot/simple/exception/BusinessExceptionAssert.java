package com.springboot.simple.exception;

public interface BusinessExceptionAssert extends IResultEnum,Assert {

    @Override
    default BusinessException newException() {
        return new BusinessException(this);
    }

    @Override
    default BusinessException newException(Integer code, String message){
        return new BusinessException(code,message);
    }

    @Override
    default BusinessException newException(String message){
        return new BusinessException(this.getCode(),message);
    }

}
