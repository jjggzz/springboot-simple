package com.springboot.simple.jdbc.service;

/**
 * @author JGZ
 * CreateTime 2020/3/26 20:52
 */
public interface BaseService<T,E> {

    int insert(T model);

    int delete(Long id);

    int update(T model);

    T selectByPrimaryKey(Long id);

    int count(E example);


}
