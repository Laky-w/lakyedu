package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.RoleAuthority;

public interface RoleAuthorityDao {

    int insertRoleAuthority(RoleAuthority roleAuthority);


    RoleAuthority queryRoleAuthorityById(Integer id);

    int updateByPrimaryKeySelective(RoleAuthority roleAuthority);

    int updateByPrimaryKey(RoleAuthority roleAuthority);
}