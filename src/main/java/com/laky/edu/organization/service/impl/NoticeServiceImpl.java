package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.Notice;
import com.laky.edu.organization.dao.NoticeDao;
import com.laky.edu.organization.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    private NoticeDao noticeDao;


    @Override
    public int deleteByLastDatetime(Date lastDatetime) throws Exception {
        return noticeDao.deleteByLastDatetime(lastDatetime);
    }

    @Override
    public int add(Notice record) throws Exception {
        return noticeDao.insert(record);
    }

    @Override
    public int addSelective(Notice record) throws Exception {
        return noticeDao.insertSelective(record);
    }

    @Override
    public List<Notice> findByTheType(Integer theType) throws Exception {

        return noticeDao.queryByTheType(theType);
    }

    @Override
    public List<Notice> findByBranchOrSchool(LinkedHashMap parameterMap) throws Exception {
        return noticeDao.queryByBranchOrSchool(parameterMap);
    }

    @Override
    public int updateByPrimaryKeySelective(Notice record)  throws Exception{
        return noticeDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Notice record) throws Exception {
        return noticeDao.updateByPrimaryKey(record);
    }
}
