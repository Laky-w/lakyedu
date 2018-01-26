package com.laky.edu.teach.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Schedule;
import com.laky.edu.teach.dao.ScheduleDao;
import com.laky.edu.teach.service.ScheduleService;
import com.laky.edu.teach.web.form.ScheduleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2018/1/2.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleDao scheduleDao;

    @Transactional
    @Override
    public void doSchedule(ScheduleForm scheduleForm) throws Exception {
        //循环排课日期
        Date[] scheduleDate = scheduleForm.getScheduleDate();
        Calendar startDate =Calendar.getInstance();
        startDate.setTime(scheduleDate[0]);
        Calendar endDate =Calendar.getInstance();
        endDate.setTime(scheduleDate[1]);
        long times=endDate.getTimeInMillis()-startDate.getTimeInMillis();
        int day = (int)(times/ (1000 * 60 * 60 * 24));
        List<Map> classTimeList=scheduleForm.getClassTimes();
        List<Schedule> scheduleList = new ArrayList<>();
        for (int i=0;i<day;i++){ //365*5
            int currentWeek = startDate.get(Calendar.DAY_OF_WEEK);//周几
            for(Map map :classTimeList){
                int weekDay=(int)map.get("weekDay");
                if(currentWeek==weekDay){
                    //具体业务
                    scheduleList.add(initSchedule(scheduleForm,map,startDate));
                }
            }
            startDate.add(Calendar.DATE,1);
        }
        int rowCount=scheduleDao.batchInsert(scheduleList);
        if(rowCount>0&&scheduleForm.getHelpTeacherId() !=null&&scheduleForm.getHelpTeacherId().length>0){ //循环添加助教
            List<Map> helpTeachMap = initHelpTeach(scheduleList,scheduleForm.getHelpTeacherId());
            scheduleDao.batchHelpTeachInsert(helpTeachMap);
        }

    }

    @Transactional
    @Override
    public void doUpdateSchedule(Schedule schedule,Integer[] helpTeacherId) throws Exception {
        scheduleDao.updateByPrimaryKeySelective(schedule);
        if(helpTeacherId!=null && helpTeacherId.length>0){
            scheduleDao.deleteHelpTeach(schedule.getId());
            List<Schedule> scheduleList = new ArrayList<>();
            scheduleList.add(schedule);
            List<Map> helpTeachMap = initHelpTeach(scheduleList,helpTeacherId);
            scheduleDao.batchHelpTeachInsert(helpTeachMap);
        }
    }

    @Override
    public PageBean findALLSchedule(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(scheduleDao.selectByParameterMap(parameterMap));
    }

    @Override
    public Map findSchedule(LinkedHashMap parameterMap) throws Exception {
        return scheduleDao.selectByPrimaryKey(parameterMap);
    }

    @Override
    public int deleteSchedule(String[] ids) throws Exception {
        List<Schedule> scheduleList = new ArrayList<>();
        for(String id:ids){
            Schedule schedule = new Schedule();
            schedule.setTheStatus(0);
            schedule.setId(new Integer(id));
            scheduleList.add(schedule);
        }
        return scheduleDao.batchUpdateByPrimaryKeySelective(scheduleList);
    }

    /**
     * 初始化助教
     * @param scheduleList
     * @param helpTeachIds
     * @return
     */
    private List<Map> initHelpTeach(List<Schedule> scheduleList,Integer [] helpTeachIds){
        List<Map> helpMap = new ArrayList<>();
        for(Schedule schedule:scheduleList){
            for(int i=0;i<helpTeachIds.length;i++){
                Map map = new HashMap<>();
                map.put("scheduleId",schedule.getId());
                map.put("teachId",helpTeachIds[i]);
                helpMap.add(map);
            }
        }
        return  helpMap;
    }

    /**
     * 初始化课程
     * @param scheduleForm
     * @param timeMap
     * @param nowDay
     * @return
     */
    private Schedule initSchedule(ScheduleForm scheduleForm,Map timeMap,Calendar nowDay){
        Schedule schedule = new Schedule();
        schedule.setSchoolZoneId(scheduleForm.getSchoolZoneId());
        schedule.setTheStatus(1);
        schedule.setAttendanceStatus(1);//考勤状态
        schedule.setClassId(scheduleForm.getClassId());
        schedule.setHour(new BigDecimal(timeMap.get("courseHour").toString()));
        schedule.setRoomId(scheduleForm.getRoomId());
        schedule.setTeachId(scheduleForm.getTeacherId());
        JSONArray classTimes = (JSONArray)timeMap.get("classTime");
//        String[] classTimes=(String[]) timeMap.get("classTime");
        //上课开始时间
        nowDay.set(Calendar.HOUR,Integer.parseInt(classTimes.get(0).toString().split(":")[0]));
        nowDay.set(Calendar.MINUTE,Integer.parseInt(classTimes.get(0).toString().split(":")[1]));
        nowDay.set(Calendar.SECOND,0);
        schedule.setStartTime(nowDay.getTime());
        //上课结束时间
        nowDay.set(Calendar.HOUR,Integer.parseInt(classTimes.get(1).toString().split(":")[0]));
        nowDay.set(Calendar.MINUTE,Integer.parseInt(classTimes.get(1).toString().split(":")[1]));
        nowDay.set(Calendar.SECOND,0);
        schedule.setEndTime(nowDay.getTime());
        return  schedule;
    }

}
