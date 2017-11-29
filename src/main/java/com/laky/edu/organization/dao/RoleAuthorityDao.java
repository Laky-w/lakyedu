package com.laky.edu.organization.dao;
import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.RoleAuthority;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface RoleAuthorityDao {

    int insertRoleAuthority(RoleAuthority roleAuthority);

    Authority queryRoleAuthorityById(Integer id);

    List<RoleAuthority> queryRoleAuthorityByParameter(Integer id);

    int updateRoleAuthority(RoleAuthority roleAuthority);
}