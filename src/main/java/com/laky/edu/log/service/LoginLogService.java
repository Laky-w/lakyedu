package com.laky.edu.log.service;

import com.laky.edu.log.bean.LoginLog;
import com.laky.edu.organization.bean.User;
import org.springframework.stereotype.Component;


public interface LoginLogService {
    int deleteByPrimaryKey(Integer id);

    int insert(User user,Integer theType,String ip);

    int insertSelective(LoginLog record);

    LoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginLog record);

    int updateByPrimaryKey(LoginLog record);
}