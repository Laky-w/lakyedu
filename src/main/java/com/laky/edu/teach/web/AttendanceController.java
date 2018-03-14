package com.laky.edu.teach.web;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.BaseController;
import com.laky.edu.teach.service.AttendanceService;
import com.laky.edu.teach.web.form.AttendanceForm;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/3/10.
 */
@RestController
@RequestMapping("/teach")
public class AttendanceController extends BaseController{
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/getStudentAttendanceAll/{pageNum}/{pageSize}")
    public Map getStudentAttendanceAll(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolZoneId",super.getSchoolIds(request,2));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap =doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(attendanceService.findAttendanceStudent(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/doSaveAttendance")
    public  Map doSaveAttendance(HttpServletRequest request,String attendanceFormStr){
        try {
            AttendanceForm attendanceForm = JSON.parseObject(attendanceFormStr,AttendanceForm.class);
            attendanceService.doSaveAttendance(super.getCurrentUser(request).getId(),attendanceForm);
            return super.doWrappingData("考勤成功！");
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
}
