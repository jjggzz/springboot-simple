package com.springboot.simple.exception;

public interface Assert {
    BusinessException newException();
    BusinessException newException(Integer code,String message);
    BusinessException newException(String message);

    default void assertNotNull(Object o,String msg) {
        if (o == null) {
            throw newException(msg);
        }
    }

    default void assertNotNull(Object o) {
        if (o == null) {
            throw newException();
        }
    }


    default void assertNull(Object o,String msg) {
        if (o != null) {
            throw newException(msg);
        }
    }

    default void assertNull(Object o) {
        if (o != null) {
            throw newException();
        }
    }


    default void assertStringIsEmpty(String s,String msg) {
        if (s != null && !s.isEmpty()) {
            throw newException(msg);
        }
    }

    default void assertStringIsEmpty(String s) {
        if (s != null && !s.isEmpty()) {
            throw newException();
        }
    }


    default void assertStringNotEmpty(String s,String msg) {
        if (s == null || s.isEmpty()) {
            throw newException(msg);
        }
    }

    default void assertStringNotEmpty(String s) {
        if (s == null || s.isEmpty()) {
            throw newException();
        }
    }

}
