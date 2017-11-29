package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.UserParameter;

public interface UserParameterDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserParameter record);

    int insertSelective(UserParameter record);

    UserParameter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserParameter record);

    int updateByPrimaryKey(UserParameter record);
}