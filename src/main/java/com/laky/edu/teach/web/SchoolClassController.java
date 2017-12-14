package com.laky.edu.teach.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.service.SchoolZoneService;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.bean.SchoolClass;
import com.laky.edu.teach.service.SchoolClassService;
import com.laky.edu.teach.service.TeachService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            schoolClass=schoolClassService.createSchoolClass(schoolClass); //
            super.handleOperate("添加班级", OrganizationConst.OPERATE_ADD,"添加班级【"+schoolClass.getName()+"】",request);
            return  super.doWrappingData(schoolClass);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
