package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.dao.AuthorityDao;
import com.laky.edu.organization.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService{

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public int createAuthority(Authority authority) throws Exception {
        return 0;
    }

    @Override
    public Authority findAuthorityById(Integer id) throws Exception {
        return null;
    }

    @Override
    public List<Authority> findAuthorityByParameter(LinkedHashMap parameterMap) {
        return null;
    }

    @Override
    public int updateAuthority(Authority authority) throws Exception {
        return 0;
    }
}
