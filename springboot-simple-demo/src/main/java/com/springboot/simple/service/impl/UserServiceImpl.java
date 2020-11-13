package com.springboot.simple.service.impl;

import com.springboot.simple.exception.BusinessException;
import com.springboot.simple.jdbc.annotation.DataSource;
import com.springboot.simple.jdbc.datasource.DynamicDataSource;
import com.springboot.simple.jdbc.service.impl.BaseServiceImpl;
import com.springboot.simple.mapper.UserModelMapper;
import com.springboot.simple.model.UserModel;
import com.springboot.simple.model.UserModelExample;
import com.springboot.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JGZ
 * CreateTime 2020/3/26 21:34
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserModel, UserModelMapper, UserModelExample> implements UserService {

    @Autowired
    @Override
    public void setMapper(UserModelMapper mapper) {
        super.setMapper(mapper);
    }

    @DataSource(DynamicDataSource.SLAVE)
    @Override
    public void save(UserModel userModel){
        insert(userModel);
    }

    @Override
    public UserModel getById(Long id) {
        //抛出异常,返回给前端的是系统忙
        int i = 1/0;
        if(id == 100){
            //自定义抛出业务异常
            throw new BusinessException(1000,"信息不存在");
        }
        return selectByPrimaryKey(id);
    }

    @Override
    public Integer countUser() {
        return count(new UserModelExample());
    }


}
