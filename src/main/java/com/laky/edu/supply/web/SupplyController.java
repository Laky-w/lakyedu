package com.laky.edu.supply.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Activity;
import com.laky.edu.supply.service.SupplyService;
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
public class SupplyController extends BaseController{
    @Autowired
    private SupplyService supplyService;



    @PostMapping("/getActivityList/{pageNum}/{pageSize}")
    public Map getActivityList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(supplyService.findActivityAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createActivity")
    public Map createActivity(HttpServletRequest request, Activity activity){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            activity.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
            supplyService.createActivity(activity);
            super.handleOperate("添加市场活动", OrganizationConst.OPERATE_ADD,"添加市场活动【"+activity.getName()+"】",request);
            return super.doWrappingData(activity);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }



}
