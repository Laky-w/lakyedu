package com.laky.edu.teach.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Attendance;
import com.laky.edu.reception.dao.StudentClassDao;
import com.laky.edu.teach.bean.Schedule;
import com.laky.edu.teach.dao.AttendanceDao;
import com.laky.edu.teach.dao.ScheduleDao;
import com.laky.edu.teach.service.ScheduleService;
import com.laky.edu.teach.web.form.ScheduleForm;
import com.laky.edu.util.DateUtil;
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

    @Autowired
    private StudentClassDao studentClassDao;

    @Autowired
    private AttendanceDao attendanceDao;

    @Transactional
    @Override
    public void doSchedule(ScheduleForm scheduleForm) throws Exception {
        //循环排课日期
        Date[] scheduleDate = scheduleForm.getScheduleDate();
        Calendar startDate =Calendar.getInstance();
        startDate.setTime(scheduleDate[0]);
        Calendar endDate =Calendar.getInstance();
        endDate.setTime(scheduleDate[1]);
        long times = endDate.getTimeInMillis()-startDate.getTimeInMillis();
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
        if(scheduleList.size()==0){
            throw new RuntimeException( "上课时间短填写错误，不包含所选的星期！" );
        }
        int rowCount=scheduleDao.batchInsert(scheduleList);
        if(rowCount>0&&scheduleForm.getHelpTeacherId() !=null&&scheduleForm.getHelpTeacherId().length>0){ //循环添加助教
            List<Map> helpTeachMap = initHelpTeach(scheduleList,scheduleForm.getHelpTeacherId());
            scheduleDao.batchHelpTeachInsert(helpTeachMap);
        }
        if(rowCount>0) { //循环添加学生考勤记录
            List<Attendance> attendanceList = initScheduleAttendance(scheduleForm.getClassId(),scheduleList,new Integer[]{scheduleForm.getSchoolZoneId()});
            if(attendanceList.size()>0){
                attendanceDao.batchInsert(attendanceList);
            }
        }
    }

    @Override
    public Map doCheckedScheduleRepeat(ScheduleForm scheduleForm,Integer[] schoolIds) throws Exception {
        Map resultMap = new HashMap(  );
        resultMap.put( "code",200 );
        resultMap.put( "message","检查排课记录成功！" );
        Date[] scheduleDate = scheduleForm.getScheduleDate();
        Calendar startDate =Calendar.getInstance();
        startDate.setTime(scheduleDate[0]);
        Calendar endDate =Calendar.getInstance();
        endDate.setTime(scheduleDate[1]);
        //获取排课时间
        long times = endDate.getTimeInMillis()-startDate.getTimeInMillis();
        int day = (int)(times/ (1000 * 60 * 60 * 24));
        List<Map> classTimeList=scheduleForm.getClassTimes();
        List<Map> scheduleDateList = new ArrayList<>();
        for (int i=0;i<day;i++){ //365*5
            int currentWeek = startDate.get(Calendar.DAY_OF_WEEK);//周几
            for(Map map :classTimeList){
                int weekDay=(int)map.get("weekDay");
                if(currentWeek==weekDay){
                    JSONArray classTimes = (JSONArray)map.get("classTime");
                    Map scheduleMap = new HashMap(  );
                    //上课开始时间
                    startDate.set(Calendar.HOUR_OF_DAY,Integer.parseInt(classTimes.get(0).toString().split(":")[0]));
                    startDate.set(Calendar.MINUTE,Integer.parseInt(classTimes.get(0).toString().split(":")[1]));
                    startDate.set(Calendar.SECOND,0);
                    scheduleMap.put("startTime",startDate.getTime());
                    //上课结束时间
                    startDate.set(Calendar.HOUR_OF_DAY,Integer.parseInt(classTimes.get(1).toString().split(":")[0]));
                    startDate.set(Calendar.MINUTE,Integer.parseInt(classTimes.get(1).toString().split(":")[1]));
                    startDate.set(Calendar.SECOND,0);
                    scheduleMap.put("endTime",startDate.getTime());
                    scheduleDateList.add(scheduleMap);
                }
            }
            startDate.add(Calendar.DATE,1);
        }
        if(scheduleDateList.size()==0){
            return  resultMap;
        }
        LinkedHashMap parameterMap = new LinkedHashMap();
        parameterMap.put( "scheduleDateList",scheduleDateList);
        parameterMap.put( "roomId",scheduleForm.getRoomId() );
        parameterMap.put( "schoolZoneId",schoolIds );
        parameterMap.put( "id2", scheduleForm.getScheduleId());
        //检查教室课表
        List<Map>  schedules =scheduleDao.selectByParameterMap( parameterMap );
        if(schedules.size()>0){
            resultMap.put( "code",500 );
            String message = "";
            for(Map map : schedules){
                String dateStr = DateUtil.formatDate("yyyy-MM-dd",(Date) map.get( "startTime" ))+
                        "("+DateUtil.formatDate("HH:mm",(Date) map.get( "startTime" ))+"至"
                        +DateUtil.formatDate("HH:mm",(Date) map.get( "endTime" ))+")";
                message+="教室【"+map.get("roomName")+"】在"+dateStr+"【"+map.get("schoolClassName")+"】有课；<br/>";
            }
            resultMap.put( "message",message );
        }
        //检查班级课表
        parameterMap = new LinkedHashMap();
        parameterMap.put( "scheduleDateList",scheduleDateList);
        parameterMap.put( "schoolClassId",scheduleForm.getClassId() );
        parameterMap.put( "schoolZoneId",schoolIds );
        parameterMap.put( "id2", scheduleForm.getScheduleId());
        schedules =scheduleDao.selectByParameterMap( parameterMap );
        if(schedules.size()>0){
            String message = "";
            for(Map map : schedules){
                String dateStr = DateUtil.formatDate("yyyy-MM-dd",(Date) map.get( "startTime" ))+
                        "("+DateUtil.formatDate("HH:mm",(Date) map.get( "startTime" ))+"至"
                        +DateUtil.formatDate("HH:mm",(Date) map.get( "endTime" ))+")";
                message+="班级【"+map.get("schoolClassName")+"】在"+dateStr+"有排课；<br/>";
            }
            if((int)resultMap.get( "code" )==500){
                resultMap.put( "message",resultMap.get("message")+message );
            } else {
                resultMap.put( "code",500 );
                resultMap.put( "message",message );
            }
        }
        //检查主教课表
        parameterMap = new LinkedHashMap();
        parameterMap.put( "scheduleDateList",scheduleDateList);
        parameterMap.put( "teachId",scheduleForm.getTeacherId() );
        parameterMap.put( "schoolZoneId",schoolIds );
        parameterMap.put( "id2", scheduleForm.getScheduleId());
        schedules =scheduleDao.selectByParameterMap( parameterMap );
        if(schedules.size()>0){
            String message = "";
            for(Map map : schedules){
                String dateStr = DateUtil.formatDate("yyyy-MM-dd",(Date) map.get( "startTime" ))+
                        "("+DateUtil.formatDate("HH:mm",(Date) map.get( "startTime" ))+"至"
                        +DateUtil.formatDate("HH:mm",(Date) map.get( "endTime" ))+")";
                message+="主教【"+map.get("teachName")+"】在"+dateStr+"有排课；<br/>";
            }
            if((int)resultMap.get( "code" )==500){
                resultMap.put( "message",resultMap.get("message")+message );
            } else {
                resultMap.put( "code",500 );
                resultMap.put( "message",message );
            }
        }
        //检查助教课表
        if(scheduleForm.getHelpTeacherId()!=null && scheduleForm.getHelpTeacherId().length>0){
            Integer [] helpTeacherIds = scheduleForm.getHelpTeacherId();
            for(Integer  helpTeacherId : helpTeacherIds) {
                parameterMap = new LinkedHashMap();
                parameterMap.put( "scheduleDateList",scheduleDateList);
                parameterMap.put( "helpTeacherId",helpTeacherId );
                parameterMap.put( "schoolZoneId",schoolIds );
                parameterMap.put( "id2", scheduleForm.getScheduleId());
                schedules =scheduleDao.selectByParameterMap( parameterMap );
                if(schedules.size()>0){
                    String message = "";
                    for(Map map : schedules){
                        String dateStr = DateUtil.formatDate("yyyy-MM-dd",(Date) map.get( "startTime" ))+
                                "("+DateUtil.formatDate("HH:mm",(Date) map.get( "startTime" ))+"至"
                                +DateUtil.formatDate("HH:mm",(Date) map.get( "endTime" ))+")";
                        message+="助教【"+map.get("helpTeacherName")+"】在"+dateStr+"有排课；<br/>";
                    }
                    if((int)resultMap.get( "code" )==500){
                        resultMap.put( "message",resultMap.get("message")+message );
                    } else {
                        resultMap.put( "code",500 );
                        resultMap.put( "message",message );
                    }
                }
            }
        }
        return resultMap;
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
        nowDay.set(Calendar.HOUR_OF_DAY,Integer.parseInt(classTimes.get(0).toString().split(":")[0]));
        nowDay.set(Calendar.MINUTE,Integer.parseInt(classTimes.get(0).toString().split(":")[1]));
        nowDay.set(Calendar.SECOND,0);
        schedule.setStartTime(nowDay.getTime());
        //上课结束时间
        nowDay.set(Calendar.HOUR_OF_DAY,Integer.parseInt(classTimes.get(1).toString().split(":")[0]));
        nowDay.set(Calendar.MINUTE,Integer.parseInt(classTimes.get(1).toString().split(":")[1]));
        nowDay.set(Calendar.SECOND,0);
        schedule.setEndTime(nowDay.getTime());
        return  schedule;
    }

    /**
     * 初始化考勤
     * @return
     * @throws Exception
     */
    private List<Attendance> initScheduleAttendance(Integer schoolClassId, List<Schedule> scheduleList,Integer[] schoolIds) throws Exception{
        LinkedHashMap parameterMap = new LinkedHashMap();
        parameterMap.put("schoolZoneId",schoolIds);
        parameterMap.put("classId",schoolClassId);
        parameterMap.put("classStatus",1);//在读状态
        List<Map> studentList= studentClassDao.selectByParameterMap(parameterMap);
        List<Attendance> attendanceList = new ArrayList<>();
        if(studentList!=null && studentList.size()>0) {
            for (Map map : studentList) {
                for (Schedule schedule : scheduleList) {
                    Attendance attendance = new Attendance();
                    attendance.setStudentId((Integer) map.get("studentId"));
                    attendance.setTheStatus(1);//未删除记录
                    attendance.setAttendanceStatus(1);//考勤状态
                    attendance.setScheduleId(schedule.getId());
                    attendanceList.add(attendance);
                }
            }
        }
        return attendanceList;
    }
}
