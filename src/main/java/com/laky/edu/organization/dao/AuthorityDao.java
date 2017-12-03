package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.Authority;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface AuthorityDao {


    int insertAuthority(Authority authority);

    Authority queryAuthorityById(Integer id);

    List<Authority> queryAuthorityByParameter(LinkedHashMap parameterMap);

    int updateAuthority(Authority authority);
}