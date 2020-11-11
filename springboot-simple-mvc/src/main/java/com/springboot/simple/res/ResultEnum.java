package com.springboot.simple.res;

/**
 * @author jgz
 * CreateTime 2020/4/23 11:08
 */
public enum ResultEnum {

    /**
     * 成功返回
     */
    SUCCESS(1,"SYS_OK","操作成功！！"),
    /**
     * 失败返回
     */
    ERROR(0,"SYS_ERROR","服务器正在忙，请稍后再试！！");

    private Integer status;
    private String code;
    private String message;

    ResultEnum(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
