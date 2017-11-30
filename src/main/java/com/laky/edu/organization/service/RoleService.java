package com.laky.edu.organization.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.Role;
import com.laky.edu.organization.bean.RoleAuthority;

import java.util.LinkedHashMap;
import java.util.List;

public interface RoleService {

    int createRole(Role role);

    Authority findRoleById(Integer id);

    List<RoleAuthority> findRoleByParameter(LinkedHashMap parameterMap);

    int updateRole(Role role);

    /**
     * 查询校区角色
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean findRoleBySchool(LinkedHashMap parameterMap) throws  Exception;
}