package com.laky.edu.supply.web;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Contact;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.service.ContactService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
            parameterMap.put("schoolIds",super.getSchoolIds(request,2));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(contactService.findContactAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getContactView/{id}")
    public Map getContactView(HttpServletRequest request, @PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request,2));
            parameterMap.put("id",id);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(contactService.findContactById(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createContact")
    public Map createContact(HttpServletRequest request, Contact contact){
        try {
            contact.setUserId(getCurrentUser(request).getId());
            contactService.createContact(contact);
            String logTitle ="添加沟通记录";
            String logContent ="添加沟通内容【"+contact.getContent()+"】";
            if(contact.getId()!=null){
                logTitle ="修改沟通记录";
                logContent ="修改沟通内容【"+contact.getContent()+"】";
            }
            super.handleOperate(logTitle, OrganizationConst.OPERATE_ADD,logContent,request);
            return super.doWrappingData(contact);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }



    @DeleteMapping("/deleteContent/{ids}")
    public Map deleteContent(HttpServletRequest request,@PathVariable String ids){
        try {
            if(!StringUtils.isEmpty(ids)){
                contactService.deleteContact(ids.split(","));
            } else {
                throw new Exception("删除记录出错！");
            }
            super.handleOperate("删除沟通记录", OrganizationConst.OPERATE_DELETE,"删除沟通记录",request);
            return super.doWrappingData("删除成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}