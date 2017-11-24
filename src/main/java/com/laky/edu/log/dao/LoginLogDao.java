package com.laky.edu.log.dao;

import com.laky.edu.log.bean.LoginLog;
import org.springframework.stereotype.Component;

@Component
public interface LoginLogDao {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginLog record);

    int insertSelective(LoginLog record);

    LoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginLog record);

    int updateByPrimaryKey(LoginLog record);
}