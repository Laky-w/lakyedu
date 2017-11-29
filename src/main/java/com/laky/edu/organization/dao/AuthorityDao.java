package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.Authority;
import org.springframework.stereotype.Component;

@Component
public interface AuthorityDao {


    int insertAuthority(Authority authority);



    Authority queryAuthorityById(Integer id);

    int updateByPrimaryKeySelective(Authority authority);

    int updateByPrimaryKey(Authority authority);
}