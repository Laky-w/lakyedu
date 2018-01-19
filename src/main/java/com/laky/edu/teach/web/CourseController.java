package com.laky.edu.teach.web;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.User;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.service.CourseService;
import com.laky.edu.teach.service.TeachService;
import com.laky.edu.teach.web.form.CourseForm;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/1/19.
 */
@RestController
@RequestMapping("/teach")
public class CourseController extends BaseController{
    @Autowired
    private CourseService courseService;

    @PostMapping("/getCourseList/{pageNum}/{pageSize}")
    public Map getCourseList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("schoolZoneId",super.getSchoolIds(request,2));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(courseService.findCourseBySchoolZone(parameterMap));
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
            return super.doWrappingData(courseService.findCourseByBranch(parameterMap));
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
            return super.doWrappingData(courseService.findCourseTreeByBranch(parameterMap));
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
            return super.doWrappingData(courseService.findCourseSchool(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createCourse")
    public Map createCourse(HttpServletRequest request,String  courseJson){
        try {
            CourseForm courseForm = JSON.parseObject(courseJson,CourseForm.class);
            Course course = courseForm.getCourse();
            if(course.getId()!=null){ //修改
                course.setBranchId(super.getCurrentUser(request).getBranchId());
                course =courseService.updateCourse(course,courseForm.getSchoolIds(),courseForm.getChargeStandard());
                super.handleOperate("修改课程", OrganizationConst.OPERATE_UPDATE,"修改课程【"+course.getName()+"】",request);
            } else {
                course.setBranchId(super.getCurrentUser(request).getBranchId());
                course =courseService.createCourse(course,courseForm.getSchoolIds(),courseForm.getChargeStandard());
                super.handleOperate("添加课程", OrganizationConst.OPERATE_ADD,"添加课程【"+course.getName()+"】",request);
            }
            return super.doWrappingData(course);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PutMapping("/updateCourseSchool/{courseId}")
    public Map updateCourseSchool(HttpServletRequest request,@PathVariable Integer courseId,Integer[] schoolIds){
        try {
            courseService.updateCourseSchool(courseId,schoolIds);
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
            courseMap.put("course",courseService.queryCourse(parameterMap));
            courseMap.put("courseSchool",courseService.findCourseSchool(parameterMap));
            courseMap.put("chargeStandards",courseService.findChargeStandardByCourseId(parameterMap));
            return super.doWrappingData(courseMap);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping(value = "deleteCourse/{courseId}")
    public Map deleteCourse(HttpServletRequest request, @PathVariable Integer courseId){
        try {
            User user =super.getCurrentUser(request);

            String  log= courseService.deleteCourse(courseId,user.getBranchId());
            handleOperate("删除课程",OrganizationConst.OPERATE_DELETE,log,request);
            return  super.doWrappingData("删除成功");
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
    @PutMapping(value = "normalCourse/{courseId}")
    public Map normalCourse(HttpServletRequest request, @PathVariable Integer courseId){
        try {
            User user =super.getCurrentUser(request);
            String  log= courseService.sealUpOrNormalCourse(courseId,user.getBranchId(),1);
            handleOperate("启用课程",OrganizationConst.OPERATE_UPDATE,log,request);
            return  super.doWrappingData("启用成功");
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
    @PutMapping(value = "sealUpCourse/{courseId}")
    public Map sealUpCourse(HttpServletRequest request, @PathVariable Integer courseId){
        try {
            User user =super.getCurrentUser(request);
            String  log= courseService.sealUpOrNormalCourse(courseId,user.getBranchId(),2);
            handleOperate("封存课程",OrganizationConst.OPERATE_UPDATE,log,request);
            return  super.doWrappingData("封存成功");
        } catch (Exception e){
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
            return super.doWrappingData(courseService.findChargeStandardByCourseId(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
