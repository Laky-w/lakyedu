package com.laky.edu.organization.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
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
    public int createNotice(Notice notice) throws Exception {
        return noticeDao.insertNotice(notice);
    }

    @Override
    public PageBean findNoticeByBranchOrSchool(LinkedHashMap parameterMap) throws Exception {
        int pageNum = (int) parameterMap.get("pageNum");
        int pageSize = (int) parameterMap.get("pageSize");
        PageHelper.startPage(pageNum,pageSize);
        return new PageBean(noticeDao.queryNoticeByBranchOrSchool(parameterMap));
    }

    @Override
    public List<Notice> findNewNotice(LinkedHashMap parameterMap) throws Exception {
        return noticeDao.queryNewNoticeByBranchOrSchool(parameterMap);
    }

    @Override
    public List<Notice> findNoticeAll(LinkedHashMap parameterMap) throws Exception {
        return noticeDao.queryNoticeAll(parameterMap);
    }

    @Override
    public Notice findNoticeById(Integer id) throws Exception {
        return noticeDao.queryNoticeById(id);
    }

    @Override
    public int updateNoticeByPrimaryKeySelective(Notice notice) throws Exception {
        return noticeDao.updateNoticeByPrimaryKeySelective(notice);
    }

    @Override
    public int updateNoticeByPrimaryKey(Notice notice) throws Exception {
        return noticeDao.updateNoticeByPrimaryKey(notice);
    }

    @Override
    public int deleteNoticeByLastDatetime(Date lastDatetime) throws Exception {
        return noticeDao.deleteNoticeByLastDatetime(lastDatetime);
    }
}
