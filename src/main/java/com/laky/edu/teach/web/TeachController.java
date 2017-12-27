package com.laky.edu.teach.web;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.service.TeachService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
            return super.doWrappingData(teachService.findCourseByBranch(parameterMap));
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
    @GetMapping("/getCourseTreeList")
    public Map getCourseTreeList(HttpServletRequest request){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            return super.doWrappingData(teachService.findCourseTreeByBranch(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createCourse")
    public Map createCourse(HttpServletRequest request, Course course){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
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

    @PostMapping("/getRoomList/{pageNum}/{pageSize}")
    public Map getRoomList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            parameterMap.put("schoolIds",super.getSchoolIds(request,parameterMap.get("theType"),parameterMap.get("parentSchoolId")));
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
            LinkedHashMap parameterMap = new LinkedHashMap();
            room=teachService.createRoom(room); //
            super.handleOperate("添加教室", OrganizationConst.OPERATE_ADD,"添加教室【"+room.getName()+"】",request);
            return  super.doWrappingData(room);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
