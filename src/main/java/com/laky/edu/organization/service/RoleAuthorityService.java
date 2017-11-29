package com.laky.edu.organization.service;

import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.RoleAuthority;

import java.util.LinkedHashMap;
import java.util.List;

public interface RoleAuthorityService {

    int createRoleAuthority(RoleAuthority roleAuthority);

    Authority findRoleAuthorityById(Integer id);

    List<RoleAuthority> findRoleAuthorityByParameter(LinkedHashMap parameterMap);

    int updateRoleAuthority(RoleAuthority roleAuthority);
}