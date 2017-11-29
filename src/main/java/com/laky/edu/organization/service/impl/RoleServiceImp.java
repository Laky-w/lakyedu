package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.Role;
import com.laky.edu.organization.bean.RoleAuthority;
import com.laky.edu.organization.dao.RoleDao;
import com.laky.edu.organization.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService{

    @Autowired
    private RoleDao roleDao;

    @Override
    public int createRole(Role role) {
        return 0;
    }

    @Override
    public Authority findRoleById(Integer id) {
        return null;
    }

    @Override
    public List<RoleAuthority> findRoleByParameter(LinkedHashMap parameterMap) {
        return null;
    }

    @Override
    public int updateRole(Role role) {
        return 0;
    }


}
