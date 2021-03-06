package com.laky.edu.organization.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.Role;
import com.laky.edu.organization.bean.RoleAuthority;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public interface RoleService {

    /**
     * 创建角色
     * @param role
     * @param authorities
     * @return
     */
    Role createRole(Role role ,Integer [] authorities) throws Exception;

    /**
     * 修改角色
     * @param role
     * @param authorities
     * @return
     */
    Role updateRole(Role role ,Integer [] authorities) throws Exception;



    Role findRoleById(Integer id);

    List<RoleAuthority> findRoleByParameter(LinkedHashMap parameterMap);

    int updateRole(Role role);

    /**
     * 查询校区角色
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Object findRoleBySchool(LinkedHashMap parameterMap) throws  Exception;

    /**
     * 删除角色
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteRole(Integer id) throws Exception;
}