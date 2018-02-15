package com.laky.edu.teach.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Schedule;
import com.laky.edu.teach.web.form.ScheduleForm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/1/2.
 */
public interface ScheduleService {
    /**
     * 班级排课
     */
    void doSchedule(ScheduleForm scheduleForm) throws Exception;

    /**
     *修改课表
     */
    void doUpdateSchedule(Schedule schedule,Integer[] helpTeacherId) throws Exception;

    /**
     * 查询排课
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean findALLSchedule(LinkedHashMap parameterMap)throws Exception;

    /**
     * 检查课表是否重复
     * @param scheduleForm
     * @param schoolIds
     * @return
     * @throws Exception
     */
    Map doCheckedScheduleRepeat(ScheduleForm scheduleForm,Integer[] schoolIds)throws Exception;

    /**
     * 查询排课
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Map findSchedule(LinkedHashMap parameterMap)throws Exception;

    /**
     * 删除排课记录
     * @param ids
     * @return
     * @throws Exception
     */
    int deleteSchedule(String [] ids) throws Exception;
}
