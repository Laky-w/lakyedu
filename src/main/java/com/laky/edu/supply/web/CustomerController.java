package com.laky.edu.supply.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.User;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.service.CustomerService;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.service.TeachService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2017/12/10.
 */
@RestController
@RequestMapping("/supply")
public class CustomerController extends BaseController{
    @Autowired
    private CustomerService customerService;



    @PostMapping("/getCustomerList/{pageNum}/{pageSize}")
    public Map getCustomerList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request,2));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(customerService.findCustomerAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createCustomer")
    public Map createCustomer(HttpServletRequest request, Customer customer,Integer [] intentionId ){
        try {
            if(customer.getId() == null ){ //添加
                customer.setCreateUserId(super.getCurrentUser(request).getId());
                customer=customerService.createCustomer(customer,intentionId);
                super.handleOperate("添加生源", OrganizationConst.OPERATE_ADD,"添加生源【"+customer.getName()+"】",request);
            } else {
                customer=customerService.updateCustomer(customer,intentionId);
                super.handleOperate("修改生源", OrganizationConst.OPERATE_UPDATE,"修改生源【"+customer.getName()+"】",request);
            }
            return super.doWrappingData(customer);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getCustomerView/{id}")
    public Map getCustomerView(HttpServletRequest request,@PathVariable Integer  id ){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("schoolZoneId",getSchoolIds(request,2));
            return super.doWrappingData(customerService.findCustomer(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
    @DeleteMapping("/deleteCustomer/{id}")
    public Map deleteCustomer(HttpServletRequest request,@PathVariable Integer  id ){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            Map map = customerService.findCustomer(parameterMap);
            boolean flag = customerService.deleteCustomer(id);
            if(flag){
                super.handleOperate("删除生源",OrganizationConst.OPERATE_DELETE,"删除生源【"+map.get("name")+"】",request);
            }
            return super.doWrappingData("删除成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PutMapping(value = "/updateUserOwner/{ownerId}")
    public Map updateUserOwner(javax.servlet.http.HttpServletRequest request, @PathVariable Integer ownerId, Integer [] students){
        try {
            String logTitle = "分配生源";
            String logContent = "分配生源";
            customerService.updateUserOwner(ownerId,students);
            super.handleOperate(logTitle,OrganizationConst.OPERATE_UPDATE,logContent,request);
            return super.doWrappingData("操作成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/importCustomer")
    public Map importCustomer(HttpServletRequest request,@RequestParam(value="customerExcel") MultipartFile file){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            User currentUser = super.getCurrentUser(request);
            customerService.importCustomer(currentUser.getSchoolZoneId(),currentUser.getId(),file);
            return super.doWrappingData("导入成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
}
