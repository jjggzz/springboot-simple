package com.springboot.simple.res;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 统一返回实体
 * @author jgz
 * CreateTime 2020/4/23 11:05
 */
public class ResultEntity<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public ResultEntity(){}


    private ResultEntity(HttpStatus httpStatus,T data){
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
    }

    private ResultEntity(BaseResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    private ResultEntity(BaseResultEnum resultEnum,T data){
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }

    private ResultEntity(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public static <T> ResultEntity<T> success(){
        return new ResultEntity<T>(ResultCollection.SUCCESS);
    }

    public static <T> ResultEntity<T> success(T data){
        return new ResultEntity<T>(ResultCollection.SUCCESS,data);
    }

    public static <T> ResultEntity<T> success(BaseResultEnum resultEnum){
        return new ResultEntity<T>(resultEnum,null);
    }

    public static <T> ResultEntity<T> success(BaseResultEnum resultEnum,T data){
        return new ResultEntity<T>(resultEnum,data);
    }

    public static <T> ResultEntity<T> success(HttpStatus httpStatus){
        return new ResultEntity<T>(httpStatus,null);
    }

    public static <T> ResultEntity<T> success(HttpStatus httpStatus,T data){
        return new ResultEntity<T>(httpStatus,data);
    }

    public static <T> ResultEntity<T> failure(){
        return new ResultEntity<T>(ResultCollection.FAILURE,null);
    }

    public static <T> ResultEntity<T> failure(BaseResultEnum resultEnum){
        return new ResultEntity<T>(resultEnum,null);
    }


    public static <T> ResultEntity<T> failure(Integer code,String message){
        return new ResultEntity<T>(code,message);
    }

    public static <T> ResultEntity<T> failure(String message){
        return new ResultEntity<T>(-1,message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
