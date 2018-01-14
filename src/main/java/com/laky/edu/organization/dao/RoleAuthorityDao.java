package com.laky.edu.organization.dao;
import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.RoleAuthority;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface RoleAuthorityDao {

    int insertRoleAuthority(RoleAuthority roleAuthority);

    int insertRoleAuthorityBatch(List<RoleAuthority> roleAuthorities);

    Authority queryRoleAuthorityById(Integer id);


    int updateRoleAuthority(RoleAuthority roleAuthority);

    /**
     * 查询用户所拥有的权限
     * @param userId
     * @return
     */
    List<Map> queryRoleAuthorityByUserId(Integer userId);
}