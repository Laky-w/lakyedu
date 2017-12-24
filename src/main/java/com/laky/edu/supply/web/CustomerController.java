package com.laky.edu.supply.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.service.CustomerService;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
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
@RequestMapping("/supply")
public class CustomerController extends BaseController{
    @Autowired
    private CustomerService customerService;



    @PostMapping("/getCustomerList/{pageNum}/{pageSize}")
    public Map getCustomerList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));
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
            LinkedHashMap parameterMap = new LinkedHashMap();
            customer.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
            customer=customerService.createCustomer(customer,intentionId);
            super.handleOperate("添加生源", OrganizationConst.OPERATE_ADD,"添加生源【"+customer.getName()+"】",request);
            return super.doWrappingData(customer);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }


}
