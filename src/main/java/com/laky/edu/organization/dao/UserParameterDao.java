package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.RoleAuthority;
import com.laky.edu.organization.bean.UserParameter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface UserParameterDao {
    int insertUserParameter(UserParameter userParameter);

    Authority queryUserParameterById(Integer id);

    List<RoleAuthority> queryUserParameterByParameter(Integer id);

    int updateUserParameter(UserParameter userParameter);
}