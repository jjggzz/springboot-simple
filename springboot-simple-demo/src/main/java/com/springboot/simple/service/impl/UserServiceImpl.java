package com.springboot.simple.service.impl;

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

    @Override
    public void save(UserModel userModel){
        insert(userModel);
    }

    @Override
    public UserModel getById(Long id) {
//        if(id == 100){
//            throw new GlobalException("信息不存在");
//        }
        return selectByPrimaryKey(id);
    }

    @Override
    public Integer countUser() {
        return count(new UserModelExample());
    }


}
