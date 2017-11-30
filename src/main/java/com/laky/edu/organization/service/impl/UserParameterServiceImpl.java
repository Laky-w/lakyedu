package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.RoleAuthority;
import com.laky.edu.organization.bean.BranchParameter;
import com.laky.edu.organization.dao.BranchParameterDao;
import com.laky.edu.organization.service.UserParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class UserParameterServiceImpl implements UserParameterService{
    @Autowired
    private BranchParameterDao userParameterDao;

    @Override
    public int createUserParameter(BranchParameter userParameter) {
        return 0;
    }

    @Override
    public Authority findUserParameterById(Integer id) {
        return null;
    }

    @Override
    public List<RoleAuthority> findUserParameterByParameter(LinkedHashMap parameterMap) {
        return null;
    }

    @Override
    public int updateUserParameter(BranchParameter userParameter) {
        return 0;
    }
}
