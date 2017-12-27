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

    /**
     * 授权校区
     * @param courseSchoolList
     * @return
     */
    int insertCourseSchool(List<Map> courseSchoolList);


    /**
     * 授权收费标准
     * @param chargeStandardList
     * @return
     */
    int insertCourseChargeStandard(List<Map> chargeStandardList);

    List<Map> selectCourseChargeStandard(LinkedHashMap parameterMap);

    Course selectByPrimaryKey(Integer id);

    List<Course> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKeyWithBLOBs(Course record);

    int updateByPrimaryKey(Course record);
}