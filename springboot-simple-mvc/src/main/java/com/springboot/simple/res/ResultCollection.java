package com.springboot.simple.res;

public class ResultCollection {

    /**
     * 操作成功
     */
    public static final DefaultResult SUCCESS = new DefaultResult(1,"操作成功");

    /**
     * 操作失败
     */
    public static final DefaultResult FAILURE = new DefaultResult(-1,"操作失败");

    public static class DefaultResult implements BaseResultEnum {

        private Integer code;

        private String message;

        public DefaultResult(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public Integer getCode() {
            return null;
        }

        @Override
        public String getMessage() {
            return null;
        }
    }
}
