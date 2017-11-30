package com.laky.edu.organization.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.*;
import com.laky.edu.core.PageBean;
import com.laky.edu.organization.dao.*;
import com.laky.edu.organization.service.OrganizationService;
import com.laky.edu.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 95 on 2017/11/14.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    @Autowired
    private BranchDao branchDao;
    @Autowired
    private SchoolZoneDao schoolZoneDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BranchParameterDao branchParameterDao;
    @Autowired
    private BranchParameterValueDao branchParameterValueDao;

    @Override
    public Branch findBranchBySerial(String serial) {
        return branchDao.queryBranchBySerial(serial);
    }

    @Override
    public Branch findBranchBySerialOrId(String serial, Integer id) {
        LinkedHashMap parameter = new LinkedHashMap();
        parameter.put("id",id);
        parameter.put("serial",serial);
        return branchDao.queryBranchBySerialOrId(parameter);
    }

    @Override
    @Transactional
    public int createNewBranch(Branch branch) {
        branch.setCreateDatetime(new Date());
        branch.setLogo("logo");
        // branch.setLastDatetime(new Date());
        branch.setMaxCount(10); // 默认10个校区
        branch.setTheStatus(OrganizationConst.BRANCH_STATUS_NORMAL); // 校区状态（1正常，2 封停，0 删除）
        //插入机构
        int rowCount= branchDao.insertBranch(branch);
        //处理签约人业务（提成，发短信。。）
        //初始化机构总部校区
        SchoolZone schoolZone = new SchoolZone();
        schoolZone.setName("总部");
        schoolZone.setTheType(OrganizationConst.SCHOOL_ZONE_TYPE_HQ);//1 总部，2 分校，3 部门
        schoolZone.setTheStatus(OrganizationConst.SCHOOL_ZONE_STATUS_NORMAL);
        schoolZone.setSerial("001");
        schoolZone.setBranch(branch);
        schoolZone.setCreateDatetime(new Date());
        schoolZone.setAddress(branch.getAddress());
        schoolZone.setPhone(branch.getPhone());
        schoolZoneDao.insertSchoolZoneDao(schoolZone);
        //初始化总部管理员
        User user = new User();
        user.setUserName("admin");
        user.setNickName("管理员");
        user.setName(branch.getOwner());
        user.setPassword(MD5.getMd5("123456"));
        user.setTheStatus(OrganizationConst.USER_STATUS_NORMAL);
        user.setBranch(branch);
        user.setSchoolZone(schoolZone);
        user.setCreateDatetime(new Date());
        user.setPhone(schoolZone.getPhone());
        user.setSex(1);
        user.setIsSuper(OrganizationConst.USER_SUPER_YES);
        userDao.insertUser(user);
        return rowCount;
    }

    @Override
    @Transactional
    public int updateBranchById(Branch branch) throws Exception{
        int rowCount = branchDao.updateBranchById(branch);
      //  lakyTestDao.queryLakyTest();
        if(1==1){
            logger.error("更新机构，事务执行失败！");
            throw new NullPointerException("事务执行失败");
        }
        return rowCount;
    }

    @Override
    public PageBean findBranchAll(int pageNum, int pageSize) throws Exception {
       // PageHelper pageHelper = new PageHelper();
        PageHelper.startPage(pageNum, pageSize);
        List<Branch> dataList= branchDao.findBranchAll();
     //   new PageInfo<>(dataList);

        return new PageBean<>(dataList);
    }

    @Override
    public List<Branch> findBranchAll(LinkedHashMap parameterMap) throws Exception {
        List<Branch> dataList= branchDao.findBranchAll();
        return dataList;
    }

    @Override
    public List<BranchParameter> findBranchParameterAll() throws Exception {
        return branchParameterDao.queryBranchParameterAll();
    }

    @Override
    public List<BranchParameterValue> findBranchParameterValueAll(LinkedHashMap parameterMap) throws Exception {
        return branchParameterValueDao.queryBranchParameterValueByBranchId(parameterMap);
    }
}
