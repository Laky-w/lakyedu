package com.laky.edu.supply.web;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Contact;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.service.ContactService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            contact.setUserId(getCurrentUser(request).getId());
            contactService.createContact(contact);
            super.handleOperate("添加跟进记录", OrganizationConst.OPERATE_ADD,"添加跟进内容【"+getCurrentUser(request).getName()+"】",request);
            return super.doWrappingData(contact);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}