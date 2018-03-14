package com.laky.edu.teach.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.teach.web.form.AttendanceForm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/3/10.
 */
public interface AttendanceService {
    /**
     * 查询考勤学员
     * @param parameterMap
     * @return
     */
    PageBean<Map> findAttendanceStudent(LinkedHashMap parameterMap)throws  Exception;

    /**
     * 保存考勤信息
     * @param userId
     * @param attendanceForm
     * @throws Exception
     */
    void doSaveAttendance(Integer userId, AttendanceForm attendanceForm)throws Exception;
}
