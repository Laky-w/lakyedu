package com.laky.edu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laky.edu.bean.Branch;
import com.laky.edu.core.PageBean;
import com.laky.edu.dao.BranchDao;
import com.laky.edu.dao.LakyTestDao;
import com.laky.edu.service.OrganizationService;
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
    private LakyTestDao lakyTestDao;

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
        branch.setTheStatus(1); // 校区状态（1正常，2 封停，0 删除）
        //插入机构
        int rowCount= branchDao.insertBranch(branch);
        //处理签约人业务（提成，发短信。。）
        //初始化机构总部校区和超级管理员
        return rowCount;
    }

    @Override
    @Transactional
    public int updateBranchById(Branch branch) throws Exception{
        int rowCount = branchDao.updateBranchById(branch);
        lakyTestDao.queryLakyTest();
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
}
