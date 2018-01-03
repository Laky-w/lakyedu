package com.laky.edu.teach.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.teach.web.form.ScheduleForm;

import java.util.LinkedHashMap;

/**
 * Created by 湖之教育工作室·laky on 2018/1/2.
 */
public interface ScheduleService {
    /**
     * 班级排课
     */
    void doSchedule(ScheduleForm scheduleForm) throws Exception;

    /**
     * 查询排课
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean findALLSchedule(LinkedHashMap parameterMap)throws Exception;
}
