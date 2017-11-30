package com.laky.edu.organization.dao;
import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.Role;
import com.laky.edu.organization.bean.RoleAuthority;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface RoleDao {

    int insertRole(Role role);

    Authority queryRoleById(Integer id);

    /**
     * 查询权限
     * @param parameterMap
     * @return
     */
    List<RoleAuthority> queryRoleByParameter(LinkedHashMap parameterMap);

    int updateRole(Role role);
}