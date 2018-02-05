package com.laky.edu.organization.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.Role;
import com.laky.edu.organization.bean.RoleAuthority;
import com.laky.edu.organization.dao.RoleAuthorityDao;
import com.laky.edu.organization.dao.RoleDao;
import com.laky.edu.organization.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService{

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleAuthorityDao roleAuthorityDao;

    @Transactional
    @Override
    public Role createRole( Role role ,Integer [] authorities) throws Exception {
        role.setTheType(1);//岗位类型
        role.setTheStatus(1);
        role.setCreateTime(new Date());
        int rows=roleDao.insertRole(role);
        if(rows==0) throw new RuntimeException("职能创建失败");
        List<RoleAuthority> dataList=getRoleAuthority(role,authorities);
        roleAuthorityDao.insertRoleAuthorityBatch(dataList);
        role.setRoleAuthorities(dataList);
        return role;
    }

    @Override
    public Role updateRole(Role role, Integer[] authorities) throws Exception {
        int rows=roleDao.updateByPrimaryKeySelective(role);
        if(rows==0) throw new RuntimeException("职能修改失败");
        roleAuthorityDao.deleteRoleAuthorityByRoleId(role.getId());
        List<RoleAuthority> dataList=getRoleAuthority(role,authorities);
        roleAuthorityDao.insertRoleAuthorityBatch(dataList);
        role.setRoleAuthorities(dataList);
        return role;
    }

    private List<RoleAuthority> getRoleAuthority(Role role, Integer[] authorities) throws Exception {
        List<RoleAuthority> dataList = new ArrayList<>();
        for(Integer authority:authorities){
            RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setAuthorityId(authority);
            roleAuthority.setRole(role);
            dataList.add(roleAuthority);
        }
        return  dataList;
    }

    @Override
    public Role findRoleById(Integer id) {
        Role role = roleDao.queryRoleById(id);
        List<RoleAuthority> roleAuthorityList = roleAuthorityDao.queryRoleAuthorityByRoleId(id);
        role.setRoleAuthorities(roleAuthorityList);
        return role;
    }

    @Override
    public List<RoleAuthority> findRoleByParameter(LinkedHashMap parameterMap) {
        return null;
    }

    @Override
    public int updateRole(Role role) {
        return 0;
    }

    @Override
    public Object findRoleBySchool(LinkedHashMap parameterMap) throws Exception {
        Object pageNum= parameterMap.get("pageNum");
        Object pageSize= parameterMap.get("pageSize");
        if( null != pageNum && null != pageSize){
            PageHelper.startPage((int)pageNum,(int)pageSize);
            return new PageBean(roleDao.queryRoleByParameter(parameterMap));
        } else {
            return roleDao.queryRoleByParameter(parameterMap);
        }
    }

    @Transactional
    @Override
    public boolean deleteRole(Integer id) throws Exception {
        Role role = new Role();
        role.setId(id);
        role.setTheStatus(0);
        int rowCount = roleDao.updateRole(role);
        return rowCount!=0;
    }
}
