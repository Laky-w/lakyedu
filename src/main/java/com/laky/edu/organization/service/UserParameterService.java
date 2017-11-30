package com.laky.edu.organization.service;

import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.RoleAuthority;
import com.laky.edu.organization.bean.BranchParameter;

import java.util.LinkedHashMap;
import java.util.List;

public interface UserParameterService {
    int createUserParameter(BranchParameter userParameter);

    Authority findUserParameterById(Integer id);

    List<RoleAuthority> findUserParameterByParameter(LinkedHashMap parameterMap);

    int updateUserParameter(BranchParameter userParameter);
}