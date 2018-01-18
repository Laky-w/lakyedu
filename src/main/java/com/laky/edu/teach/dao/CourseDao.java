package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.Course;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface CourseDao {

    int insert(Course record);

    int insertCourseSchool(List<Map> courseSchoolList);

    int insertCourseChargeStandard(List<Map> chargeStandardList);

    int deleteCourseSchool(Integer courseId);

    List<Map> selectCourseChargeStandard(LinkedHashMap parameterMap);

    List<Map> selectCourseSchool(LinkedHashMap parameterMap);

    Map selectCourse(LinkedHashMap parameterMap);

    List<Course> selectByParameterMap(LinkedHashMap parameterMap);

    List<Course> selectBranchByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}