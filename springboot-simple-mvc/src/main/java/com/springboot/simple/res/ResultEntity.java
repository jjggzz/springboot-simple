package com.springboot.simple.res;

/**
 * 统一返回实体
 * @author jgz
 * CreateTime 2020/4/23 11:05
 */
public class ResultEntity<T> {

    /**
     * 状态码
     */
    private Integer status;
    /**
     * code
     */
    private String code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;


    public ResultEntity(ResultEnum resultEnum){
        this(resultEnum,null);
    }
    public ResultEntity(ResultEnum resultEnum,T data){
        this(resultEnum.getStatus(),resultEnum.getCode(),resultEnum.getMessage(),data);
    }

    public ResultEntity(Integer status, String code, String message) {
        this(status,code,message,null);
    }

    public ResultEntity(Integer status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultEntity() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
