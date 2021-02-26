package com.springboot.simple.res;

import com.springboot.simple.exception.IResultEnum;
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

    private ResultEntity(HttpStatus httpStatus,T data){
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
    }

    private ResultEntity(IResultEnum iResultEnum){
        this.code = iResultEnum.getCode();
        this.message = iResultEnum.getMessage();
    }

    private ResultEntity(IResultEnum iResultEnum, T data){
        this.code = iResultEnum.getCode();
        this.message = iResultEnum.getMessage();
        this.data = data;
    }

    private ResultEntity(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public static <T> ResultEntity<T> success(){
        return new ResultEntity<>(BaseResultEnum.SUCCESS);
    }

    public static <T> ResultEntity<T> success(T data){
        return new ResultEntity<>(BaseResultEnum.SUCCESS,data);
    }

    public static <T> ResultEntity<T> success(IResultEnum iResultEnum){
        return new ResultEntity<>(iResultEnum,null);
    }

    public static <T> ResultEntity<T> success(IResultEnum iResultEnum,T data){
        return new ResultEntity<>(iResultEnum,data);
    }

    public static <T> ResultEntity<T> success(HttpStatus httpStatus){
        return new ResultEntity<>(httpStatus,null);
    }

    public static <T> ResultEntity<T> success(HttpStatus httpStatus,T data){
        return new ResultEntity<>(httpStatus,data);
    }

    public static <T> ResultEntity<T> failure(){
        return new ResultEntity<>(BaseResultEnum.FAILURE,null);
    }

    public static <T> ResultEntity<T> failure(IResultEnum iResultEnum){
        return new ResultEntity<>(iResultEnum,null);
    }


    public static <T> ResultEntity<T> failure(Integer code,String message){
        return new ResultEntity<>(code,message);
    }

    public static <T> ResultEntity<T> failure(String message){
        return new ResultEntity<>(BaseResultEnum.FAILURE.getCode(),message);
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
