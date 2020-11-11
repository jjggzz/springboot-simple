package com.springboot.simple.exception;

/**
 * @author jgz
 * CreateTime 2020/4/23 11:26
 */
public class GlobalException extends RuntimeException {
    private Integer status = -1;
    private String code = "SYS_HINT";
    private String message;

    public GlobalException(String message) {
        super(message);
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
