package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.Attendance;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface AttendanceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Attendance record);

    int batchInsert(List<Attendance> attendanceList);

    int insertSelective(Attendance record);

    Attendance selectByPrimaryKey(Integer id);

    List selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Attendance record);

    int batchUpdateByPrimaryKeySelective(List<Attendance> attendanceList);

    int updateByPrimaryKey(Attendance record);
}