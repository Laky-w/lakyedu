package com.laky.edu.organization.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.Menu;
import com.laky.edu.organization.bean.User;

import java.util.LinkedHashMap;
import java.util.List;

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
     * 查询全部菜单
     * @return
     * @throws Exception
     */
    List<Menu> findMenuAll() throws Exception;

    /**
     * 查询校区用户
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean findUserBySchoolId(LinkedHashMap parameterMap)throws Exception;
}
