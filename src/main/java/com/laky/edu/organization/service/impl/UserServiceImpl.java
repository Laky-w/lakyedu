package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.Menu;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.dao.MenuDao;
import com.laky.edu.organization.dao.UserDao;
import com.laky.edu.organization.service.UserService;
import com.laky.edu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/11/21.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    @Override
    public User loginUser(String userName, String pwd,String serial) throws Exception{
        LinkedHashMap parameterMap = new LinkedHashMap();
        parameterMap.put("userName",userName);
        parameterMap.put("serial",serial);
        User user=userDao.queryUserByUserName(parameterMap);
        if (null == user) {
           throw new Exception("用户名错误。");
        } else if (!MD5.getMd5(pwd).equals(user.getPassword())){
            throw new Exception("密码错误。");
        } else if (OrganizationConst.USER_STATUS_SEALUP== user.getTheStatus()) {
            throw new Exception("用户已禁用,请联系机构管理员。");
        } else {
            return user;
        }
    }

    @Override
    public List<Menu> findMenuAll() throws Exception {
        //递归处理菜单
        return menuDao.findMenuAll();
    }
}
