package com.springboot.simple.mapper;

import com.springboot.simple.jdbc.mapper.BaseModelMapper;
import com.springboot.simple.model.UserModel;
import com.springboot.simple.model.UserModelExample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserModelMapper extends BaseModelMapper<UserModel,UserModelExample> {
}