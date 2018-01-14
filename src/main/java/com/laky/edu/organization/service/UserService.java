package com.laky.edu.organization.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.Menu;
import com.laky.edu.organization.bean.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/11/21.
 */
public interface UserService {
    /**
     *  用户登录
     * @param userName
     * @param pwd
     * @param serial
     * @return
     * @throws Exception
     */
    User loginUser(String userName,String pwd,String serial) throws Exception;

    /**
     * 查询全部菜单和权限
     * @return
     * @throws Exception
     */
    List<Menu> findMenuAll() throws Exception;

    /**
     * 查询用户全部菜单和权限
     * @param userId
     * @return
     * @throws Exception
     */
    Map findUserMenuAll(Integer userId) throws Exception;

    /**
     * 查询校区用户
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean findUserBySchoolId(LinkedHashMap parameterMap)throws Exception;

    /**
     * 查询用户
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Map findUserById(LinkedHashMap parameterMap)throws Exception;

    /**
     * 创建一个用户
     * @param user
     * @param roles
     * @return
     * @throws Exception
     */
    User createUser(User user,Integer [] roles) throws Exception;

    /**
     * 修改一个用户
     * @param user
     * @param roles
     * @return
     * @throws Exception
     */
    User updateUser(User user,Integer [] roles) throws Exception;

    /**
     * 删除用户
     * @param userId
     * @return
     * @throws Exception
     */
    int doDeleteUser(Integer userId) throws Exception;


}
