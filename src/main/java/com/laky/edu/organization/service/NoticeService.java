package com.laky.edu.organization.service;

import com.laky.edu.organization.bean.Notice;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


public interface NoticeService {

    int deleteByLastDatetime(Date lastDatetime) throws Exception;

    int add(Notice record) throws Exception;

    int addSelective(Notice record) throws Exception;

    List<Notice> findByTheType(Integer theType) throws Exception;

    /**
     * 查找通知通过系统,学校,机构
     * @param parameterMap
     * @return
     */
    List<Notice> findByBranchOrSchool(LinkedHashMap parameterMap)throws Exception;


    int updateByPrimaryKeySelective(Notice record) throws Exception;

    int updateByPrimaryKey(Notice record) throws Exception;
}