package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.Notice;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface NoticeDao {
    /**
     * 插入一条数据
     * @param notice
     * @return
     */
    int insertNotice(Notice notice );

    /**
     * 查找通知通过系统,学校,机构
     * @param parameterMap
     * @return
     */
    List<Notice> queryNoticeByBranchOrSchool(LinkedHashMap parameterMap);

    /**
     * 查询公告
     * @param parameterMap
     * @return
     */
    Map queryNoticeById(LinkedHashMap parameterMap);


    /**
     * 查询全部公告
     * @param parameter
     * @return
     */
    List<Notice> queryNoticeAll(LinkedHashMap parameter);
    /**
     * 查询最新公告
     * @param parameterMap
     * @return
     */
    List<Notice> queryNewNoticeByBranchOrSchool(LinkedHashMap parameterMap);

    /**
     * 更新通过主键细选
     * @param notice
     * @return
     */
    int updateNoticeByPrimaryKeySelective(Notice notice);

    /**
     * 更新通过主键
     * @param notice
     * @return
     */
    int updateNoticeByPrimaryKey(Notice notice);

    /**
     * 删除通过最后时间
     * @param lastDatetime
     * @return
     */
    int deleteNoticeByLastDatetime(Date lastDatetime);
}