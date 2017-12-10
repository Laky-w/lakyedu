package com.laky.edu.teach.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.dao.CourseDao;
import com.laky.edu.teach.dao.RoomDao;
import com.laky.edu.teach.service.TeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/12/10.
 */
@Service
public class TeachServiceImpl implements TeachService{
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private RoomDao roomDao;

    @Override
    public PageBean<Course> findCourseByBranch(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(courseDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public Course createCourse(Course course) throws Exception {
        course.setTheStatus(1);
        int rows=courseDao.insert(course);
        if(rows==0) throw new Exception("创建课程失败！");
        return course;
    }

    @Override
    public PageBean<Room> findRoomAll(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(roomDao.selectByParameterMap(parameterMap));
    }

    @Override
    public Room createRoom(Room room) throws Exception {
        room.setTheStatus(1);
        int rows=roomDao.insert(room);
        if(rows==0) throw new Exception("创建教室失败！");
        return room;
    }
}
