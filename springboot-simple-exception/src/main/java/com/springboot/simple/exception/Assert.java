package com.springboot.simple.exception;

public interface Assert {
    BusinessException newException();
    BusinessException newException(Integer code,String message);

    static void assertNotNull(Object o) {
        if (o == null) {
        }
    }
}
