package com.laky.edu.supply.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Activity;
import com.laky.edu.supply.bean.Invite;
import com.laky.edu.supply.service.InviteService;
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
public class InviteController extends BaseController{

    @Autowired
    private InviteService inviteService;

    @PostMapping("/getInviteList/{pageNum}/{pageSize}")
    public Map getInviteList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(inviteService.findByInviteAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createInvite")
    public Map createInvite(HttpServletRequest request, Invite invite){
        try {
            invite.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
            invite.setInviteStatus(1);
            invite.setUserId(super.getCurrentUser(request).getId());
            inviteService.addInvite(invite);
//
            super.handleOperate("添加邀请试听", OrganizationConst.OPERATE_ADD,"添加邀约参观,参观人【"+invite.getUserId()+"】,参观时间:"+invite.getInviteTime(),request);
            return super.doWrappingData(invite);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
