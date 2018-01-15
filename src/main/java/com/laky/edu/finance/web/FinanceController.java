package com.laky.edu.finance.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.finance.bean.FinanceAccount;
import com.laky.edu.finance.service.FinanceService;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.teach.bean.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getMoneyRecordAccountList/{recordId}")
    public Map getMoneyRecordAccountList(HttpServletRequest request, @PathVariable int recordId){
        try {
            return super.doWrappingData(financeService.findMoneyRecordAccountList(recordId));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getMoneyRecordList/{pageNum}/{pageSize}")
    public Map getMoneyRecordList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolZoneId",super.getSchoolIds(request, OrganizationConst.SCHOOL_ZONE_TYPE_CHILD));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(financeService.findMoneyRecordAllBySchool(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createFinanceAccount")
    public Map createFinanceAccount(org.apache.catalina.servlet4preview.http.HttpServletRequest request, FinanceAccount account){
        try {
            boolean isOpen =Boolean.valueOf(request.getParameter("isOpen"));
            boolean isPublic = Boolean.valueOf(request.getParameter("isPublic"));
            if(isOpen) account.setTheOpen(1);//前台账户
            else account.setTheOpen(2);
            if(isPublic) account.setThePublic(1); //公共账户
            else account.setThePublic(2);
            if (account.getId()==null){
                account.setBranchId(super.getCurrentUser(request).getBranchId());
                account=financeService.createFinanceAccount(account);
                super.handleOperate("添加账户", OrganizationConst.OPERATE_ADD,"添加账户【"+account.getName()+"】",request);
            }else {
                financeService.updateFinanceAccount(account);
                super.handleOperate("修改账户", OrganizationConst.OPERATE_ADD,"修改账户【"+account.getName()+"】",request);
            }
            return super.doWrappingData(account);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping("/deleteFinanceAccount/{id}")
    public Map deleteFinanceAccount(HttpServletRequest request,@PathVariable Integer id){
        try{
            FinanceAccount financeAccount = new FinanceAccount();
            financeService.deleteFinanceAccount(id);
            super.handleOperate("删除财务账户", OrganizationConst.OPERATE_ADD,"删除财务账户【"+financeAccount.getName()+"】",request);
            return super.doWrappingData("操作成功");
        }catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getFinanceAccountView/{id}")
    public Map getFinanceAccountView(HttpServletRequest request, @PathVariable Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("branchId",getCurrentUser(request).getBranchId());
            parameterMap.put("schoolZoneId",getSchoolIds(request,2));
            return super.doWrappingData(financeService.selectFinanceAccount(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
