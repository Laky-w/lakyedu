package com.laky.edu.supply.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Activity;
import com.laky.edu.supply.bean.Invite;
import com.laky.edu.supply.service.InviteService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
            parameterMap.put("schoolIds",super.getSchoolIds(request,2));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(inviteService.findInviteAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getInviteView/{id}")
    public Map getInviteView(HttpServletRequest request, @PathVariable int id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request,2));
            parameterMap.put("id",id);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(inviteService.findInviteById(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createInvite")
    public Map createInvite(HttpServletRequest request, Invite invite){
        try {
            if(invite.getId()!=null){
                inviteService.updateInvite(invite);
                super.handleOperate("修改邀请参观", OrganizationConst.OPERATE_UPDATE,"修改邀约参观,参观人【"+invite.getStudentName()+"】,参观时间:"+invite.getInviteTime(),request);
            } else {
                invite.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
                invite.setInviteStatus(1);
                invite.setUserId(super.getCurrentUser(request).getId());
                inviteService.addInvite(invite);
                super.handleOperate("添加邀请参观", OrganizationConst.OPERATE_ADD,"添加邀约参观,参观人【"+invite.getStudentName()+"】,参观时间:"+invite.getInviteTime(),request);
            }

            return super.doWrappingData(invite);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping("/deleteInvite/{ids}")
    public Map deleteInvite(HttpServletRequest request,@PathVariable String ids){
        try {
            if(!StringUtils.isEmpty(ids)){
                inviteService.deleteInvite(ids.split(","));
            } else {
                throw new Exception("删除记录出错！");
            }
            super.handleOperate("删除参观记录", OrganizationConst.OPERATE_DELETE,"删除参观记录",request);
            return super.doWrappingData("删除成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
