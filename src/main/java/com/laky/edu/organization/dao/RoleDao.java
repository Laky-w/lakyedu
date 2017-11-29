package com.laky.edu.organization.dao;
import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.Role;
import com.laky.edu.organization.bean.RoleAuthority;
import java.util.LinkedHashMap;
import java.util.List;

public interface RoleDao {

    int insertRole(Role role);

    Authority queryRoleById(Integer id);

    List<RoleAuthority> queryRoleByParameter(Integer id);

    int updateRole(Role role);
}