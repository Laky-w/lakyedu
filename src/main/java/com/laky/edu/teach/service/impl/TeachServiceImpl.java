package com.laky.edu.teach.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.bean.ScheduleStandard;
import com.laky.edu.teach.dao.CourseDao;
import com.laky.edu.teach.dao.RoomDao;
import com.laky.edu.teach.dao.ScheduleStandardDao;
import com.laky.edu.teach.service.TeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2017/12/10.
 */
@Service
public class TeachServiceImpl implements TeachService{
    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ScheduleStandardDao scheduleStandardDao;

    @Override
    public PageBean<Room> findRoomAll(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(roomDao.selectByParameterMap(parameterMap));
    }
    @Transactional
    @Override
    public Room createRoom(Room room) throws Exception {
        room.setTheStatus(1);
        int rows=roomDao.insertRoom(room);
        if(rows==0) throw new Exception("创建教室失败！");
        return room;
    }

    @Override
    public Map findRoom(LinkedHashMap parameterMap) {
        return roomDao.selectRoom(parameterMap);
    }

    @Transactional
    @Override
    public int deleteRoom(Integer id) throws Exception {
        Room room = new Room();
        room.setId(id);
        room.setTheStatus(0);
        int rows = roomDao.updateRoom(room);
        if (rows==0)throw new RuntimeException("删除教室失败");
        return rows;
    }
    @Transactional
    @Override
    public Room updateRoom(Room room) throws Exception {
        int rows = roomDao.updateRoom(room);
        if (rows==0)throw  new Exception("更新教室");
        return room;
    }



    @Override
    public PageBean<ScheduleStandard> findScheduleStandardAll(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(scheduleStandardDao.selectByParameterMap(parameterMap));
    }

    @Override
    public Map findScheduleStandard(LinkedHashMap parameterMap) throws Exception {
        return scheduleStandardDao.selectScheduleStandard(parameterMap);
    }

    @Transactional
    @Override
    public ScheduleStandard createScheduleStandard(ScheduleStandard scheduleStandard) throws Exception {
        scheduleStandard.setTheStatus(1);
        int rowCount=scheduleStandardDao.insert(scheduleStandard);
        if(rowCount==0){
            throw new Exception("创建校区上课时间段失败！");
        }
        return scheduleStandard;
    }

    @Transactional
    @Override
    public int deleteScheduleStandardById(Integer id) throws Exception{
        ScheduleStandard scheduleStandard = new ScheduleStandard();
        scheduleStandard.setId(id);
        scheduleStandard.setTheStatus(0);
        int rows = scheduleStandardDao.updateScheduleStandard(scheduleStandard);
        if (rows==0) throw new RuntimeException("删除排课失败!");
        return rows;
    }
    @Transactional
    @Override
    public ScheduleStandard updateScheduleStandard(ScheduleStandard scheduleStandard) throws Exception{
        int rows =scheduleStandardDao.updateScheduleStandard(scheduleStandard);
        if (rows==0) throw new RuntimeException("修改上课时间失败!");
        return scheduleStandard;
    }
}
