package com.laky.edu.log.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.log.bean.LoginLog;
import com.laky.edu.log.dao.LoginLogDao;
import com.laky.edu.log.service.LoginLogService;
import com.laky.edu.organization.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService{
    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(User user,Integer theType,String ip) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setSchoolZoneId(user.getSchoolZoneId());
        loginLog.setBranchId(user.getBranchId());
        loginLog.setTheType(theType);
        loginLog.setIp(ip);
        loginLog.setTheDatetime(new Date());
        return loginLogDao.insert(loginLog);
    }

    @Override
    public int insertSelective(LoginLog record) {
        return 0;
    }

    @Override
    public LoginLog selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public PageBean selectByParameter(LinkedHashMap parameterMap) throws Exception {
        int pageNum = (int)parameterMap.get("pageNum");
        int pageSize = (int)parameterMap.get("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        return new PageBean(loginLogDao.selectByParameter(parameterMap));
    }

    @Override
    public int updateByPrimaryKeySelective(LoginLog record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(LoginLog record) {
        return 0;
    }
}