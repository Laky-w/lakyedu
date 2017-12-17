package com.laky.edu.supply.web;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Contact;
import com.laky.edu.supply.service.ContactService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/supply")
public class ContactController extends BaseController{

    @Autowired
    private ContactService contactService;

    @PostMapping("/getContactList/{pageNum}/{pageSize}")
    public Map getContactList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(contactService.findContactAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createContact")
    public Map createContact(HttpServletRequest request, Contact contact){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            contactService.createContact(contact);
            super.handleOperate("添加联系人", OrganizationConst.OPERATE_ADD,"添加联系人【"+getCurrentUser(request).getUserName()+"】",request);
            return super.doWrappingData(contact);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}