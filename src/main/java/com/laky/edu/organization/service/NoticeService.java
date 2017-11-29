package com.laky.edu.organization.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.Notice;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


public interface NoticeService {

    /**
     * 插入一条数据
     * @param notice
     * @return
     */
    int createNotice(Notice notice )throws Exception;

    /**
     * 查找通知通过系统,学校,机构
     * @param parameterMap
     * @return
     */
    PageBean findNoticeByBranchOrSchool(LinkedHashMap parameterMap)throws Exception;

    /**
     * 查询通过id
     * @param id
     * @return
     */
    Notice findNoticeById(Integer id)throws Exception;
    /**
     * 更新通过主键细选
     * @param notice
     * @return
     */
    int updateNoticeByPrimaryKeySelective(Notice notice)throws Exception;

    /**
     * 更新通过主键
     * @param notice
     * @return
     */
    int updateNoticeByPrimaryKey(Notice notice)throws Exception;

    /**
     * 删除通过最后时间
     * @param lastDatetime
     * @return
     */
    int deleteNoticeByLastDatetime(Date lastDatetime)throws Exception;
}