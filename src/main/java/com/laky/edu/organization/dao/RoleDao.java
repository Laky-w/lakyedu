package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.Role;

public interface RoleDao {

    int insertRole(Role role);

    Role queryRoleById(Integer id);

    int updateByPrimaryKeySelective(Role role);

    int updateByPrimaryKey(Role role);
}