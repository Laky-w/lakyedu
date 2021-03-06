package com.laky.edu.teach.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.service.SchoolZoneService;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.bean.ScheduleStandard;
import com.laky.edu.teach.bean.SchoolClass;
import com.laky.edu.teach.service.SchoolClassService;
import com.laky.edu.teach.service.TeachService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/10.
 */
@RestController
@RequestMapping("/teach")
public class SchoolClassController extends BaseController{
    @Autowired
    private TeachService teachService;

    @Autowired
    private SchoolClassService schoolClassService;

    @PostMapping("/getSchoolClassList/{pageNum}/{pageSize}")
    public Map getSchoolClassList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(schoolClassService.findSchoolClassAllBySchool(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createSchoolClass")
    public Map createSchoolClass(HttpServletRequest request, SchoolClass schoolClass){
        try {
            if (schoolClass.getId()==null){
                schoolClass=schoolClassService.createSchoolClass(schoolClass); //
                super.handleOperate("添加班级", OrganizationConst.OPERATE_ADD,"添加班级【"+schoolClass.getName()+"】",request);
            }else {
                schoolClassService.updateSchoolZOne(schoolClass);
                super.handleOperate("修改班级", OrganizationConst.OPERATE_ADD,"修改班级【"+schoolClass.getName()+"】",request);
            }
            return  super.doWrappingData(schoolClass);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getSchoolClassView/{id}")
    public Map getSchoolClassView(HttpServletRequest request, @PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("schoolZoneId",getSchoolIds(request,2));
            return  super.doWrappingData(schoolClassService.findSchoolClassById(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getWaitStudentList/{pageNum}/{pageSize}")
    public Map getWaitStudentList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolZoneId",super.getSchoolIds(request));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(schoolClassService.findWaitStudentAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping("deleteSchoolClass/{id}")
    public Map deleteSchoolClass(HttpServletRequest request,@PathVariable Integer id){
        try {
            SchoolClass schoolClass = new SchoolClass();
            schoolClassService.deleteSchoolZone(id);
            super.handleOperate("删除班级",OrganizationConst.OPERATE_DELETE,"删除班级【"+schoolClass.getName()+"】",request);
            return super.doWrappingData("操作成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PutMapping("updateStudentClass")
    public Map updateStudentClass(HttpServletRequest request,String studentClassIds,Integer classId){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolZoneId",super.getSchoolIds(request));
            parameterMap.put("ids",studentClassIds.split(","));
            parameterMap.put("classId",classId);
            schoolClassService.doUpdateStudentClass(parameterMap);
            super.handleOperate("学员分班",OrganizationConst.OPERATE_UPDATE,"学员分班",request);
            return super.doWrappingData("操作成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
}
