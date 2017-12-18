package com.laky.edu.finance.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.finance.bean.FinanceAccount;
import com.laky.edu.finance.service.FinanceService;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.teach.bean.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/18.
 */
@RestController
@RequestMapping(value = "/finance")
public class FinanceController extends BaseController {
    @Autowired
    private FinanceService financeService;

    @PostMapping("/getFinanceAccountList/{pageNum}/{pageSize}")
    public Map getFinanceAccountList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("schoolZoneId",super.getSchoolIds(request, OrganizationConst.SCHOOL_ZONE_TYPE_CHILD));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(financeService.findFinanceAccountAllBySchool(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createFinanceAccount")
    public Map createFinanceAccount(org.apache.catalina.servlet4preview.http.HttpServletRequest request, FinanceAccount account){
        try {
            account.setBranchId(super.getCurrentUser(request).getBranchId());
            boolean isOpen =Boolean.valueOf(request.getParameter("isOpen"));
            boolean isPublic = Boolean.valueOf(request.getParameter("isPublic"));
            if(isOpen) account.setTheOpen(1);//前台账户
            else account.setTheOpen(2);
            if(isPublic) account.setThePublic(1); //公共账户
            else account.setThePublic(2);
            account=financeService.createFinanceAccount(account);
            super.handleOperate("添加账户", OrganizationConst.OPERATE_ADD,"添加账户【"+account.getName()+"】",request);
            return super.doWrappingData(account);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
