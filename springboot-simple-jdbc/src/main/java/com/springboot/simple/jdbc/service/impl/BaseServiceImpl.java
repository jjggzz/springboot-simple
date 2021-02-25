package com.springboot.simple.jdbc.service.impl;

import com.springboot.simple.jdbc.mapper.BaseModelMapper;
import com.springboot.simple.jdbc.model.BaseModel;
import com.springboot.simple.jdbc.model.BaseModelExample;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author JGZ
 * CreateTime 2020/3/26 20:54
 */
public class BaseServiceImpl<T extends BaseModel,D extends BaseModelMapper<T,E>, E extends BaseModelExample>  {

    protected D mapper;

    protected Supplier<E> exampleSupplier;

    protected Supplier<T> modelSupplier;

    public void setMapper(D mapper) {
        this.mapper = mapper;
    }

    public void setExampleSupplier(Supplier<E> supplier) {
        this.exampleSupplier = supplier;
    }

    public void setModelSupplier(Supplier<T> supplier) {
        this.modelSupplier = supplier;
    }

    public T newModel() {
        return modelSupplier.get();
    }

    public E newExample() {
        return exampleSupplier.get();
    }

    public int insert(T model){
        model.setCreateTime(new Date(System.currentTimeMillis()));
        model.setModifiedTime(new Date(System.currentTimeMillis()));
        model.setDeleted(false);
        return this.mapper.insertSelective(model);
    }

    public int delete(Long id){
        return this.mapper.deleteByPrimaryKey(id);
    }

    public int update(T model){
        model.setModifiedTime(new Date(System.currentTimeMillis()));
        return this.mapper.updateByPrimaryKeySelective(model);
    }

    public  T selectByPrimaryKey(Long id){
        return this.mapper.selectByPrimaryKey(id);
    }

    List<T> selectByExample(E example){
        return this.mapper.selectByExample(example);
    }

    public int count(E example){
        return this.mapper.countByExample(example);
    }

}
