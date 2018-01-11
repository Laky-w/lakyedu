package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.bean.ScheduleStandard;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface ScheduleStandardDao {


    /**
     * 增加上课时间
     * @param record
     * @return
     */
    int insert(ScheduleStandard record);

    int insertSelective(ScheduleStandard record);

    /**
     * 查询上课时间详情页
     * @param parameterMap
     * @return
     */
    Map selectScheduleStandard(LinkedHashMap  parameterMap);

    /**
     * 查询上课时间
     * @param parameterMap
     * @return
     */
    List<ScheduleStandard> selectByParameterMap(LinkedHashMap parameterMap);

    /**
     * 修改上课时间
     * @param scheduleStandard
     * @return
     */
    int updateScheduleStandard(ScheduleStandard scheduleStandard);

    int updateByPrimaryKey(ScheduleStandard record);
}