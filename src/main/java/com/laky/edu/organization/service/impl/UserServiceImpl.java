package com.laky.edu.organization.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.Menu;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.dao.MenuDao;
import com.laky.edu.organization.dao.UserDao;
import com.laky.edu.organization.service.UserService;
import com.laky.edu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
        List<Menu> menuList =menuDao.findMenuAll();
        List<Menu> newMenuList = new ArrayList<>();
        menuList.forEach(item->{
            if(item.getParentId() == 0){
                item.setSubs(getSubs(item.getId(),menuList));
                newMenuList.add(item);
            }
        });
       /* for (Menu menu: menuList){
            if (menu.getParentId() == 0) { //父级菜单
                menu.setSubs(getSubs(menu.getId(),menuList));
                newMenuList.add(menu);
            }
        }*/
        newMenuList.sort((a,b) -> a.getSort().compareTo(b.getSort()));
        return newMenuList;
    }

    /**
     * 查找孩子节点
     * @param id
     * @param menuList
     * @return
     */
    private List<Menu> getSubs(Integer id,List<Menu> menuList){
        List<Menu> newMenuList = new ArrayList<>();
        menuList.forEach(item->{
            if(item.getParentId() == id){
                item.setSubs(getSubs(item.getId(),menuList));
                newMenuList.add(item);
            }
        });
        if (newMenuList.size() == 0) return  null;
        newMenuList.sort((a,b) -> a.getSort().compareTo(b.getSort()));
        return newMenuList;
    }

    @Override
    public PageBean findUserBySchoolId(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(userDao.queryUserBySchoolId(parameterMap));
    }

    @Transactional
    @Override
    public User createUser(User user) throws Exception {
        user.setTheStatus(1);
        user.setCreateDatetime(new Date());
        //010
        user.setIsSuper(2);
        user.setPassword(MD5.getMd5("123456"));
        int row= userDao.insertUser(user);
        return row>0 ?user: null;
    }
}

