package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.Notice;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface NoticeDao {
    int deleteByLastDatetime(Date lastDatetime);

    int insert(Notice record);

    int insertSelective(Notice record);

    List<Notice> queryByTheType(Integer theType);

    /**
     * 查找通知通过系统,学校,机构
     * @param parameterMap
     * @return
     */
    List<Notice> queryByBranchOrSchool(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);
}