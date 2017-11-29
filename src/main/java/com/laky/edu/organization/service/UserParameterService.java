package com.laky.edu.organization.service;

import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.RoleAuthority;
import com.laky.edu.organization.bean.UserParameter;

import java.util.LinkedHashMap;
import java.util.List;

public interface UserParameterService {
    int createUserParameter(UserParameter userParameter);

    Authority findUserParameterById(Integer id);

    List<RoleAuthority> findUserParameterByParameter(LinkedHashMap parameterMap);

    int updateUserParameter(UserParameter userParameter);
}