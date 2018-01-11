package com.laky.edu.supply.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Activity;
import com.laky.edu.supply.service.SupplyService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
            Date date = new Date();
            Date startDate = activity.getStartDate();
            Date endDate  = activity.getEndDate();
            if (startDate.getTime()>date.getTime()){//计划中
                activity.setTheType(1);
            }else if(endDate!=null&&startDate.getTime()<date.getTime() && date.getTime()<endDate.getTime()){//进行中
                activity.setTheType(2);
            }else if (endDate!=null&&endDate.getTime()<=date.getTime()){//结束
                activity.setTheType(3);
            }
            if (activity.getId()==null){
                activity.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
                supplyService.createActivity(activity);
                super.handleOperate("添加市场活动", OrganizationConst.OPERATE_ADD,"添加市场活动【"+activity.getName()+"】",request);
            }else {
                supplyService.updateByPrimaryKeySelective(activity);
                super.handleOperate("修改市场活动", OrganizationConst.OPERATE_ADD,"修改市场活动【"+activity.getName()+"】",request);
            }
            return super.doWrappingData(activity);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

//    private int

    @DeleteMapping("/deleteActivity/{id}")
    public Map deleteActivity(javax.servlet.http.HttpServletRequest request, @PathVariable(required = true) Integer id){
        try {
            Activity activity = new Activity();
            supplyService.deleteActivity(id);
            super.handleOperate("删除市场活动",OrganizationConst.OPERATE_DELETE,"删除市场活动【"+activity.getName()+"】",request);
            return super.doWrappingData("操作成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }


    @GetMapping("/getMarketActivityView/{id}")
    public Map getMarketActivityView(HttpServletRequest request,@PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("schoolZoneId",getSchoolIds(request,2));

            return super.doWrappingData(supplyService.queryActivity(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
