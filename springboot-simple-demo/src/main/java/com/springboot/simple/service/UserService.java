package com.springboot.simple.service;

import com.springboot.simple.jdbc.service.BaseService;
import com.springboot.simple.model.UserModel;
import com.springboot.simple.model.UserModelExample;

/**
 * @author JGZ
 * CreateTime 2020/3/26 21:33
 */
public interface UserService extends BaseService<UserModel, UserModelExample> {

    void save(UserModel userModel);

    UserModel getById(Long id);

    Integer countUser();

}
