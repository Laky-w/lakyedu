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

    /**
     * 删除授权校区
     * @param courseId
     * @return
     */
    int deleteCourseSchool(Integer courseId);

    /**
     * 删除课程标准
     * @param courseId
     * @return
     */
    int deleteChargeStandard(Integer courseId);

    List<Map> selectCourseChargeStandard(LinkedHashMap parameterMap);

    List<Map> selectCourseSchool(LinkedHashMap parameterMap);

    Course selectCourseById(Integer id);

    Map selectCourse(LinkedHashMap parameterMap);

    List<Course> selectCourseAll(LinkedHashMap parameterMap);

    List<Course> selectByParameterMap(LinkedHashMap parameterMap);

    List<Course> selectBranchByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}