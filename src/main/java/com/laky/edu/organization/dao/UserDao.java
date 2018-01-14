package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.User;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/11/21.
 */
@Component
public interface UserDao {
    /**
     * 插入一个用户
     * @param user
     * @return
     */
    int insertUser(User user);


    User selectById(Integer id);

    /**
     * 查询用户
     * @param parameter 参数
     * @return
     */
    Map queryUserById(LinkedHashMap parameter);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据机构用户名查询
     * @param parameter 参数
     * @return
     */
    User queryUserByUserName(LinkedHashMap parameter);

    /**
     * 根据校区查询
     * @param parameter 参数
     * @return
     */
    List<User> queryUserBySchoolId(LinkedHashMap parameter);


}
