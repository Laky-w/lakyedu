package com.laky.edu.teach.web;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.teach.bean.SchoolClass;
import com.laky.edu.teach.service.ScheduleService;
import com.laky.edu.teach.web.form.ScheduleForm;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            scheduleService.doSchedule(scheduleForm);
            super.handleOperate("添加课表", OrganizationConst.OPERATE_ADD,"排课班级【"+scheduleForm.getClassName()+"】",request);
            return  super.doWrappingData("添加成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
    @PostMapping("/getClassSchedule/{pageNum}/{pageSize}")
    public Map getClassSchedule(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
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

}
