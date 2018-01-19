package com.laky.edu.teach.service;

import com.alibaba.fastjson.JSONArray;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.bean.ScheduleStandard;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/10.
 */

public interface TeachService {

    /**
     * 查询教室
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
     * 教室详情页
     * @param parameterMap
     * @return
     */
    Map findRoom(LinkedHashMap parameterMap);
    /**
     * 删除教室
     * @param id
     * @return
     * @throws Exception
     */
    int deleteRoom(Integer id)throws Exception;

    /**
     * 更改教室
     * @param room
     * @return
     * @throws Exception
     */
    Room updateRoom(Room room)throws Exception;


    /**
     * 查询排课标准
     * @param parameterMap
     * @return
     */
    PageBean<ScheduleStandard> findScheduleStandardAll(LinkedHashMap parameterMap);

    /**
     * 查询上课时间段标准详情
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Map findScheduleStandard(LinkedHashMap parameterMap)throws Exception;

    /**
     * 创建上课时间段标准
     * @param scheduleStandard
     * @return
     */
    ScheduleStandard createScheduleStandard(ScheduleStandard scheduleStandard) throws Exception;

    /**
     * 删除上课时间段标准
     * @param id
     * @return
     */
    int deleteScheduleStandardById(Integer id)throws Exception;

    /**
     * 修改上课时间段标准
     * @param scheduleStandard
     * @return
     */
    ScheduleStandard updateScheduleStandard(ScheduleStandard scheduleStandard)throws Exception;
}
