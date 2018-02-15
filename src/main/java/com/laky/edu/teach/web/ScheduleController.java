package com.laky.edu.teach.web;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.teach.bean.Schedule;
import com.laky.edu.teach.bean.SchoolClass;
import com.laky.edu.teach.service.ScheduleService;
import com.laky.edu.teach.web.form.ScheduleForm;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/1/2.
 */
@RequestMapping("/teach")
@RestController
public class ScheduleController extends BaseController{
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/createClassSchedule")
    public Map createClassSchedule(HttpServletRequest request,String scheduleFormStr){
        try {
            ScheduleForm scheduleForm = JSON.parseObject(scheduleFormStr,ScheduleForm.class);
            scheduleForm.setSchoolZoneId(getCurrentUser(request).getSchoolZoneId());
//            if(scheduleForm.getChecked() !=null && scheduleForm.getChecked() == true){ //检查课表排课信息是否重复
//                Map checkedMap = scheduleService.doCheckedScheduleRepeat( scheduleForm );
//                if((int)checkedMap.get( "code" )==500) {
//                    throw new Exception( checkedMap.get( "message" )+"");
//                }
//            }
//            if(1==1) return null;
            scheduleService.doSchedule(scheduleForm);
            super.handleOperate("添加课表", OrganizationConst.OPERATE_ADD,"排课班级【"+scheduleForm.getClassName()+"】",request);
            return  super.doWrappingData("添加成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
    @PutMapping("/updateClassSchedule")
    public Map updateClassSchedule(HttpServletRequest request, Schedule schedule, Date scheduleDate,String [] scheduleTime, Integer[] helpTeacherId){
        try {
            Calendar newScheduleTime =Calendar.getInstance();
            newScheduleTime.setTime(scheduleDate);
            //上课开始时间
            newScheduleTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(scheduleTime[0].toString().split(":")[0]));//HOUR 12 小时，HOUR_OF_DAY 24小时
            newScheduleTime.set(Calendar.MINUTE,Integer.parseInt(scheduleTime[0].toString().split(":")[1]));
            newScheduleTime.set(Calendar.SECOND,0);
            schedule.setStartTime(newScheduleTime.getTime());
            //上课结束时间
            System.out.println( Integer.parseInt(scheduleTime[1].toString().split(":")[0]) );
            newScheduleTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(scheduleTime[1].toString().split(":")[0]));
            newScheduleTime.set(Calendar.MINUTE,Integer.parseInt(scheduleTime[1].toString().split(":")[1]));
            newScheduleTime.set(Calendar.SECOND,0);
            schedule.setEndTime(newScheduleTime.getTime());
            scheduleService.doUpdateSchedule(schedule, helpTeacherId);
            super.handleOperate("修改课表", OrganizationConst.OPERATE_UPDATE,"排课班级【"+schedule.getClassId()+"】",request);
            return  super.doWrappingData("修改成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    /**
     * 检查课表信息
     * @param request
     * @return
     */
    @PostMapping("/doCheckedScheduleRepeat")
    public Map doCheckedScheduleRepeat(HttpServletRequest request,String scheduleFormStr){
        try {
            ScheduleForm scheduleForm = JSON.parseObject(scheduleFormStr,ScheduleForm.class);
            scheduleForm.setSchoolZoneId(getCurrentUser(request).getSchoolZoneId());
            Map checkedMap = scheduleService.doCheckedScheduleRepeat( scheduleForm );
            return super.doWrappingData(checkedMap);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getClassScheduleAll/{pageNum}/{pageSize}")
    public Map getClassScheduleAll(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolZoneId",super.getSchoolIds(request,2));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap =doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(scheduleService.findALLSchedule(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getClassSchedule/{id}")
    public Map getClassSchedule(HttpServletRequest request, @PathVariable int id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            return super.doWrappingData(scheduleService.findSchedule(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping("/deleteClassSchedule/{ids}")
    public Map deleteClassSchedule(HttpServletRequest request,@PathVariable String ids){
        try {
            if(!StringUtils.isEmpty(ids)){
                scheduleService.deleteSchedule(ids.split(","));
            } else {
                throw new Exception("删除记录出错！");
            }
            super.handleOperate("删除排课记录", OrganizationConst.OPERATE_DELETE,"删除排课记录",request);
            return super.doWrappingData("删除成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }


}
