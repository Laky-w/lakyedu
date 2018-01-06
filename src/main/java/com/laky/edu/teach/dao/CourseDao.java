package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.Course;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface CourseDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertCourseSchool(List<Map> courseSchoolList);

    int insertCourseChargeStandard(List<Map> chargeStandardList);

    List<Map> selectCourseChargeStandard(LinkedHashMap parameterMap);

    Course selectByPrimaryKey(Integer id);

    Course selectCourse(LinkedHashMap parameterMap);

    List<Course> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKeyWithBLOBs(Course record);

    int updateByPrimaryKey(Course record);
}