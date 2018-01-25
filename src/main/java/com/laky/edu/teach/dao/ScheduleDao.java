package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.Schedule;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface ScheduleDao {

    int insert(Schedule record);

    int batchInsert(List<Schedule> scheduleList);

    int deleteHelpTeach(Integer scheduleId);

    int batchHelpTeachInsert(List<Map> helpTeachList);

    int insertSelective(Schedule record);

    Map selectByPrimaryKey(LinkedHashMap parameterMap);

    List selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Schedule record);

    int batchUpdateByPrimaryKeySelective(List<Schedule> scheduleList);

    int updateByPrimaryKey(Schedule record);
}