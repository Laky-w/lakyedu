package com.laky.edu.reception.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.reception.bean.Student;
import com.laky.edu.reception.service.StudentService;
import com.laky.edu.supply.bean.Customer;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/23.
 */
@RestController
@RequestMapping(value = "reception")
public class StudentController extends BaseController{
    @Autowired
    private StudentService studentService;

    @PostMapping("/createStudent")
    public Map createStudent(HttpServletRequest request, Student student, Customer customer){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            customer.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
            student.setSchoolZoneId(customer.getSchoolZoneId());
            studentService.createStudent(student,customer);
            super.handleOperate("添加学员", OrganizationConst.OPERATE_ADD,"添加学员【"+customer.getName()+"】",request);
            return super.doWrappingData(customer);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getStudentList/{pageNum}/{pageSize}")
    public Map getStudentList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(studentService.findStudentAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
}
