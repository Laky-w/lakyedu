package com.laky.edu.reception.web;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.BaseController;
import com.laky.edu.finance.bean.MoneyRecordAccount;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.reception.bean.Student;
import com.laky.edu.reception.bean.StudentOrder;
import com.laky.edu.reception.bean.StudentOrderDetail;
import com.laky.edu.reception.web.form.StudentApplyForm;
import com.laky.edu.reception.service.StudentService;
import com.laky.edu.supply.bean.Customer;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
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
            customer.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
            student.setSchoolZoneId(customer.getSchoolZoneId());
            if (student.getId()==null){
                studentService.createStudent(student,customer);
                super.handleOperate("添加学员", OrganizationConst.OPERATE_ADD,"添加学员【"+customer.getName()+"】",request);
            }else {
                studentService.updateStudent(student,customer);
                super.handleOperate("修改正式学员", OrganizationConst.OPERATE_UPDATE,"修改正式学员【"+student.getName()+"】",request);
            }
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

    /**
     * 学生报名
     * @return
     */
    @PostMapping("createStudentApply/{studentId}")
    public Map studentApply(HttpServletRequest request, @PathVariable Integer studentId,String form){
        try {
            StudentApplyForm applyForm= JSON.parseObject(form,StudentApplyForm.class);
            StudentOrder order= applyForm.getBill();
            order.setSchoolZoneId(getCurrentUser(request).getSchoolZoneId());
            List<StudentOrderDetail> chargeDetails = applyForm.getChargeDetails();
            List<MoneyRecordAccount> financeAccount = applyForm.getFinanceAccount();
            order = studentService.studentApply(studentId,order,chargeDetails,financeAccount,getCurrentUser(request).getId());
            super.handleOperate("学员报名", OrganizationConst.OPERATE_ADD,"报名课程：....",request);
            return super.doWrappingData(order);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    /**
     * 分配学管师
     * @param request
     * @param ownerId
     * @param students
     * @return
     */
    @PutMapping(value = "/updateStudentOwner/{ownerId}")
    public Map updateStudentOwner(javax.servlet.http.HttpServletRequest request, @PathVariable Integer ownerId, Integer [] students){
        try {
            String logTitle = "分配学管师";
            String logContent = "分配学管师";
            studentService.updateStudentManage(ownerId,students);
            super.handleOperate(logTitle,OrganizationConst.OPERATE_UPDATE,logContent,request);
            return super.doWrappingData("操作成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getStudentView/{id}")
    public Map getStudentView(HttpServletRequest request, @PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("schoolZoneId",getSchoolIds(request,2));
            return super.doWrappingData(studentService.queryStudent(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping("/deleteStudent/{id}")
    public Map deleteStudent(HttpServletRequest request,@PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            Map map = studentService.queryStudent(parameterMap);
            boolean flag = studentService.deleteStudent(map);
            if (flag) {
                super.handleOperate("删除正式学员",OrganizationConst.OPERATE_DELETE,"删除正式学员【"+map.get("name")+"】",request);
            }
            return super.doWrappingData("删除成功");
        }catch (Exception e){
            return super.doWrappingErrorData(e);
        }
    }

}
