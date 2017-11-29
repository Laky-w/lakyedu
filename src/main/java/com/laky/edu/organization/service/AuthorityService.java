package com.laky.edu.organization.service;

import com.laky.edu.organization.bean.Authority;

import java.util.LinkedHashMap;
import java.util.List;


public interface AuthorityService {


    int createAuthority(Authority authority)throws Exception;

    Authority findAuthorityById(Integer id)throws Exception;

    List<Authority> findAuthorityByParameter(LinkedHashMap parameterMap);

    int updateAuthority(Authority authority)throws Exception;
}