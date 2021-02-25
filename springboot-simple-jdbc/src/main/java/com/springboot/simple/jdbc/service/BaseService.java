package com.springboot.simple.jdbc.service;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/3/26 20:52
 */
public interface BaseService<T,E> {

    int insert(T model);

    int delete(Long id);

    int update(T model);

    T selectByPrimaryKey(Long id);

    List<T> selectByExample(E example);

    int count(E example);

}
