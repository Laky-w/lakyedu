package com.laky.edu.teach.web;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Activity;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.bean.ScheduleStandard;
import com.laky.edu.teach.service.TeachService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/10.
 */
@RestController
@RequestMapping("/teach")
public class TeachController extends BaseController{
    @Autowired
    private TeachService teachService;

    @PostMapping("/getCourseList/{pageNum}/{pageSize}")
    public Map getCourseList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("schoolZoneId",super.getSchoolIds(request,2));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(teachService.findCourseBySchoolZone(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getBranchCourseList/{pageNum}/{pageSize}")
    public Map getBranchCourseList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(teachService.findCourseByBranch(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getCourseTreeList")
    public Map getCourseTreeList(HttpServletRequest request){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("schoolZoneId",new Integer[]{super.getCurrentUser(request).getSchoolZoneId()});
            return super.doWrappingData(teachService.findCourseTreeByBranch(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getCourseSchool/{courseId}")
    public Map getCourseSchool(HttpServletRequest request,@PathVariable  Integer courseId){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("courseId",courseId);
            return super.doWrappingData(teachService.findCourseSchool(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createCourse")
    public Map createCourse(HttpServletRequest request, Course course){
        try {
            course.setBranchId(super.getCurrentUser(request).getBranchId());
            String chargeStandardStr=request.getParameter("chargeStandardStr");
            if(StringUtils.isEmpty(chargeStandardStr)) chargeStandardStr="[]";
            course =teachService.createCourse(course,request.getParameterValues("schoolIds"),JSON.parseArray(chargeStandardStr));
            super.handleOperate("添加课程", OrganizationConst.OPERATE_ADD,"添加课程【"+course.getName()+"】",request);
            return super.doWrappingData(course);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PutMapping("/updateCourseSchool/{courseId}")
    public Map updateCourseSchool(HttpServletRequest request,@PathVariable Integer courseId,Integer[] schoolIds){
        try {
            teachService.updateCourseSchool(courseId,schoolIds);
            super.handleOperate("修改课程授权校区", OrganizationConst.OPERATE_UPDATE,"修改课程授权校区",request);
            return super.doWrappingData(schoolIds);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getCourseView/{id}")
    public Map getCourseView(HttpServletRequest request,@PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("branchId",getCurrentUser(request).getBranchId());
            parameterMap.put("courseId",id);
            Map courseMap = new HashMap<>();
            courseMap.put("course",teachService.queryCourse(parameterMap));
            courseMap.put("chargeStandards",teachService.findChargeStandardByCourseId(parameterMap));
            return super.doWrappingData(courseMap);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }


    @GetMapping("/getChargeStandard/{courseId}")
    public Map getChargeStandard(HttpServletRequest request,@PathVariable Integer courseId){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
//            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            if(StringUtils.isEmpty(courseId)) throw new Exception("课程必填");
            parameterMap.put("courseId",courseId);
            return super.doWrappingData(teachService.findChargeStandardByCourseId(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createScheduleStandard")
    public Map createScheduleStandard(HttpServletRequest request, ScheduleStandard scheduleStandard,String [] time){
        try {
            if(null !=time){
                scheduleStandard.setStartTime(time[0]);
                scheduleStandard.setEndTime(time[1]);
            }
            if (scheduleStandard.getId()==null){
                scheduleStandard=teachService.createScheduleStandard(scheduleStandard); //上课时间段
                super.handleOperate("添加校区上课段", OrganizationConst.OPERATE_ADD,"添加校区上课段【"+scheduleStandard.getName()+"】",request);
            }else {
                scheduleStandard = teachService.updateScheduleStandard(scheduleStandard);
                super.handleOperate("修改校区上课段", OrganizationConst.OPERATE_UPDATE,"修改校区上课段【"+scheduleStandard.getName()+"】",request);
            }

            return  super.doWrappingData(scheduleStandard);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getScheduleStandardView/{id}")
    public Map getScheduleStandardView(HttpServletRequest request,@PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("schoolZoneId",getSchoolIds(request,2));
            return super.doWrappingData(teachService.findScheduleStandard(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getScheduleStandard/{pageNum}/{pageSize}")
    public Map getScheduleStandard(HttpServletRequest request,@PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
//            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("schoolZoneId",super.getSchoolIds(request,parameterMap.get("theType"),parameterMap.get("parentSchoolId")));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            return super.doWrappingData(teachService.findScheduleStandardAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping("/deleteScheduleStandard/{id}")
    public Map deleteScheduleStandard(javax.servlet.http.HttpServletRequest request, @PathVariable Integer id){
        try {
            ScheduleStandard scheduleStandard = new ScheduleStandard();
            teachService.deleteScheduleStandardById(id);
            super.handleOperate("删除上课时间",OrganizationConst.OPERATE_DELETE,"删除上课时间【"+scheduleStandard.getName()+"】",request);
            return super.doWrappingData("操作成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getRoomList/{pageNum}/{pageSize}")
    public Map getRoomList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            parameterMap.put("schoolZoneId",super.getSchoolIds(request,parameterMap.get("theType"),parameterMap.get("parentSchoolId")));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);

            return super.doWrappingData(teachService.findRoomAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createRoom")
    public Map createRoom(HttpServletRequest request, Room room){
        try {
            if (room.getId()==null){
                room=teachService.createRoom(room); //
                super.handleOperate("添加教室", OrganizationConst.OPERATE_ADD,"添加教室【"+room.getName()+"】",request);
            }else {
                room=teachService.updateRoom(room); //
                super.handleOperate("修改教室", OrganizationConst.OPERATE_UPDATE,"修改教室【"+room.getName()+"】",request);
            }
            return  super.doWrappingData(room);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }


    @DeleteMapping("/deleteRoom/{id}")
    public Map deleteRoom(javax.servlet.http.HttpServletRequest request, @PathVariable Integer id){
        try {
            Room room = new Room();
            teachService.deleteRoom(id);
            super.handleOperate("删除教室",OrganizationConst.OPERATE_DELETE,"删除教室【"+room.getName()+"】",request);
            return super.doWrappingData("操作成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getRoomView/{id}")
    public Map getRoomView(HttpServletRequest request,@PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("schoolZoneId",getSchoolIds(request,2));
            return super.doWrappingData(teachService.findRoom(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
