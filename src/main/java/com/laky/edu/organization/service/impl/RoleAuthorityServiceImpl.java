package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.RoleAuthority;
import com.laky.edu.organization.dao.RoleAuthorityDao;
import com.laky.edu.organization.service.RoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService{
    @Autowired
    private RoleAuthorityDao  roleAuthorityDao;

    @Override
    public int createRoleAuthority(RoleAuthority roleAuthority) {
        return 0;
    }

    @Override
    public Authority findRoleAuthorityById(Integer id) {
        return null;
    }

    @Override
    public List<RoleAuthority> findRoleAuthorityByParameter(LinkedHashMap parameterMap) {
        return null;
    }

    @Override
    public int updateRoleAuthority(RoleAuthority roleAuthority) {
        return 0;
    }
}
