package com.laky.edu.teach.service;

import com.alibaba.fastjson.JSONArray;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/10.
 */

public interface TeachService {
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
     * 查询校区教室
     * @param parameterMap
     * @return
     */
    PageBean<Room> findRoomAll(LinkedHashMap parameterMap);

    /**
     * 创建教室
     * @param room
     * @return
     * @throws Exception
     */
    Room createRoom(Room room) throws Exception;

    /**
     * 查询课程
     * @param parameterMap
     * @return 类目=》类别=》课程 三级树
     */
    List<Map> findCourseTreeByBranch(LinkedHashMap parameterMap);
}
