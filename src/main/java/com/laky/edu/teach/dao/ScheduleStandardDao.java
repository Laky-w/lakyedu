package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.bean.ScheduleStandard;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface ScheduleStandardDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleStandard record);

    int insertSelective(ScheduleStandard record);

    ScheduleStandard selectByPrimaryKey(Integer id);

    List<ScheduleStandard> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(ScheduleStandard record);

    int updateByPrimaryKey(ScheduleStandard record);
}