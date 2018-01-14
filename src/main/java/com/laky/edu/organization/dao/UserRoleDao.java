package com.laky.edu.organization.dao;
import com.laky.edu.organization.bean.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRoleDao {


    int insertUserRoleBatch(List<UserRole> roles);

    int deleteUserRole(Integer userId);

    List<UserRole> selectUserRoleById(Integer userId);

}