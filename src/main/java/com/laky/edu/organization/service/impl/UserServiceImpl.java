package com.laky.edu.organization.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.Authority;
import com.laky.edu.organization.bean.Menu;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.bean.UserRole;
import com.laky.edu.organization.dao.*;
import com.laky.edu.organization.service.UserService;
import com.laky.edu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2017/11/21.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleAuthorityDao roleAuthorityDao;

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
        List<Authority> authorities = authorityDao.queryAuthorityByParameter(null);
        menuList.forEach(item->{
            if(item.getParentId() == 0){
                List<Menu> subs=getSubs(item.getId(),menuList,authorities);
                item.setSubs(subs);
                item.setAuthorities(this.getAuthorities(item.getId(),authorities));
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


    @Override
    @Cacheable(value = "userMenu",key = "#userId+''")
    public Map findUserMenuAll(Integer userId) throws Exception {
        //判断当前用户是不是超级用户，不是去查询所拥有的权限
        User user = userDao.selectById(userId);
        if(user!=null && user.getIsSuper()==1){ //超级管理员
            Map map = new LinkedHashMap<>();
            map.put("menuList",this.findMenuAll());
            map.put("authorities","isAll");
            return map;
        } else {
            List<Map> authorityMaps= roleAuthorityDao.queryRoleAuthorityByUserId(userId);
            Map menuMap = new HashMap<>();//所有子菜单id
            authorityMaps.forEach(map -> {
                menuMap.put(map.get("menuId"),map.get("parentId"));
            });
            List<Menu> menuList =menuDao.findMenuAll();
            Set set = menuMap.keySet();
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("ids",set.toArray());
            List<Menu> childList = menuDao.findMenuAllByIds(parameterMap);
            List<Menu> parentList = new ArrayList<>();
            for(Menu menu:childList){
                parentList.addAll(getParents(menu.getParentId(),menuList,parentList));
                parentList.add(menu);
            }
            List<Menu> dataList = new ArrayList<>();
            Map map = new LinkedHashMap<>();
            parentList.forEach(item->{
                if(item.getParentId() == 0){
                    List<Menu> subs=getSubs(item.getId(),parentList,null);
                    item.setSubs(subs);
                    dataList.add(item);
                }
            });
            dataList.sort((a,b) -> a.getSort().compareTo(b.getSort()));
            map.put("menuList",dataList);
            map.put("authorities",authorityMaps);
            return  map;
        }
    }

    /**
     * 查找所有的父节点
     * @param parentId
     * @param menuList
     * @return
     */
    private List<Menu> getParents(Integer parentId,List<Menu> menuList,List<Menu> parentList){
        List<Menu> newMenu = new ArrayList<>();
        ok:
        for (Menu menu:menuList){
            if(menu.getId() == parentId){
                for (Menu pMenu:parentList){
                    if(pMenu.getId()==menu.getId()){ //已存在的根节点
                        continue ok;
                    }
                }
                newMenu.add(menu);
                if(menu.getParentId()!=0){//不是根节点
                    newMenu.addAll(getParents(menu.getParentId(),menuList,parentList));
                }
            }
        }
        return newMenu;
    }


    private List<Authority> getAuthorities(Integer menuId, List<Authority> authorities){
        List<Authority> temAuthorities = new ArrayList<Authority>();
        authorities.forEach(authority -> {
            if(authority.getMenuId()==menuId) {
                temAuthorities.add(authority);
            }
        });
        return temAuthorities.size()==0?null:temAuthorities;
    }


    /**
     * 查找孩子节点并给权限
     * @param id
     * @param menuList
     * @return
     */
    private List<Menu> getSubs(Integer id,List<Menu> menuList,List<Authority> authorities){
        List<Menu> newMenuList = new ArrayList<>();
        menuList.forEach(item->{
            if(item.getParentId() == id){
                List<Menu> subs=getSubs(item.getId(),menuList,authorities);
                item.setSubs(subs);
                if(authorities !=null){
                    item.setAuthorities(this.getAuthorities(item.getId(),authorities));
                }
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

    @Override
    public Map findUserById(LinkedHashMap parameterMap) throws Exception {
        return userDao.queryUserById(parameterMap);
    }

    @Transactional
    @Override
    public User createUser(User user,Integer [] roles) throws Exception {
        user.setTheStatus(1);
        user.setCreateDatetime(new Date());
        user.setQuitStatus(1);
        //010
        user.setPassword(MD5.getMd5("123456"));
        int row= userDao.insertUser(user);
        if(row==0) throw new Exception("用户创建失败");
        List<UserRole> dataList = getUserRoleList(user.getId(),roles);
        userRoleDao.insertUserRoleBatch(dataList);
        user.setUserRoleList(dataList);
        return user;
    }

    @Transactional
    @Override
    public User updateUser(User user, Integer[] roles) throws Exception {
        userDao.updateUser(user);
        if(roles!=null){
            userRoleDao.deleteUserRole(user.getId());//删除用户权限
            List<UserRole> dataList = getUserRoleList(user.getId(),roles);
            userRoleDao.insertUserRoleBatch(dataList);
            user.setUserRoleList(dataList);
        }
        return user;
    }

    @Transactional
    @Override
    public int doDeleteUser(Integer userId) throws Exception {
        User user = new User();
        user.setId(userId);
        user.setTheStatus(0);
        return userDao.updateUser(user);
    }

    /**
     * 封装用户权限
     * @param userId
     * @param roles
     * @return
     */
    private  List<UserRole> getUserRoleList(Integer userId,Integer[] roles){
        List<UserRole> dataList = new ArrayList<>();
        for(Integer role:roles){
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role);
            dataList.add(userRole);
        }
        return  dataList;
    }


}

