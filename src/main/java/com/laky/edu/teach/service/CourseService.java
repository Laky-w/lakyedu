package com.laky.edu.teach.service;

import com.alibaba.fastjson.JSONArray;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Course;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/1/19.
 */
public interface CourseService {
    /**
     * 查询课程
     * @param parameterMap
     * @return
     */
    PageBean<Course> findCourseBySchoolZone(LinkedHashMap parameterMap);

    /**
     * 查询课程
     * @param parameterMap
     * @return
     */
    PageBean<Course> findCourseByBranch(LinkedHashMap parameterMap);

    /**
     * 查询课程收费标准
     * @param parameterMap
     * @return
     */
    List<Map> findChargeStandardByCourseId(LinkedHashMap parameterMap);

    /**
     * 创建课程
     * @param course 课程
     * @param schoolIds 授权校区
     * @param chargeStandards 收费标准
     * @return
     * @throws Exception
     */
    Course createCourse(Course course, String[] schoolIds, JSONArray chargeStandards) throws Exception;

    /**
     * 修改课程
     * @param course 课程
     * @param schoolIds 授权校区
     * @param chargeStandards 收费标准
     * @return
     * @throws Exception
     */
    Course updateCourse(Course course, String[] schoolIds, JSONArray chargeStandards) throws Exception;

    /**
     * 删除课程
     * @param courseId
     * @param branchId
     * @return
     * @throws Exception
     */
    String deleteCourse(Integer courseId,Integer branchId)throws  Exception;

    /**
     * 启动或封存课程
     * @param courseId
     * @param branchId
     * @param theStatus
     * @return
     * @throws Exception
     */
    String sealUpOrNormalCourse(Integer courseId,Integer branchId,Integer theStatus)throws  Exception;

    /**
     * 修改授权校区
     * @param courseId
     * @param schoolIds
     * @return
     * @throws Exception
     */
    Integer [] updateCourseSchool(Integer courseId,Integer [] schoolIds) throws Exception;

    /**
     * 查询课程详情页
     * @param parameterMap
     * @return
     */
    Map queryCourse(LinkedHashMap parameterMap)throws Exception;

    /**
     * 查询课程
     * @param parameterMap
     * @return 类目=》类别=》课程 三级树
     */
    List<Map> findCourseTreeByBranch(LinkedHashMap parameterMap);

    /**
     * 查询课程授权校区
     * @param parameterMap
     * @return
     */
    List<Map> findCourseSchool(LinkedHashMap parameterMap);
}
