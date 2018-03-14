package com.laky.edu.teach.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Attendance;
import com.laky.edu.teach.bean.Schedule;
import com.laky.edu.teach.dao.AttendanceDao;
import com.laky.edu.teach.dao.ScheduleDao;
import com.laky.edu.teach.service.AttendanceService;
import com.laky.edu.teach.web.form.AttendanceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/3/10.
 */
@Service
public class AttendanceServiceImpl  implements AttendanceService{
    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public PageBean<Map> findAttendanceStudent(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(attendanceDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public void doSaveAttendance(Integer userId, AttendanceForm attendanceForm) throws Exception {
        //保存考勤信息
        List<Attendance> attendanceList = attendanceForm.getAttendanceList();
        Date nowDate = new Date();
        for (Attendance attendance:attendanceList){
            attendance.setTheTime(nowDate);
            attendance.setWay(1);//人工签到
            attendance.setOperatorId(userId);
        }
        int rowCount= attendanceDao.batchUpdateByPrimaryKeySelective(attendanceList);
        //修改课表考勤状态
        if(rowCount==0) {
            throw  new RuntimeException("考勤失败");
        }
        Schedule schedule = new Schedule();
        schedule.setId(attendanceForm.getScheduleId());
        schedule.setAttendanceStatus(2);
        scheduleDao.updateByPrimaryKeySelective(schedule);
    }
}
