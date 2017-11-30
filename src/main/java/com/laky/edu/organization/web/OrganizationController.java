package com.laky.edu.organization.web;

import com.laky.edu.organization.bean.Branch;
import com.laky.edu.organization.bean.BranchParameter;
import com.laky.edu.organization.service.OrganizationService;
import com.laky.edu.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95 on 2017/11/14.
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping(value = "createNewBranch")
    public Map createNewBranch(Branch branch){
        try {

            int rowCount=organizationService.createNewBranch(branch);
            if(rowCount>0){
                return  super.doWrappingData(branch);
            } else {
                throw new Exception("创建机构失败！");
            }
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }

    }
    @GetMapping(value = "getBranchBySerial/{serial}")
    public Map findBranchBySerial(@PathVariable String serial){
        try {
            Branch branch= organizationService.findBranchBySerial(serial);
            return super.doWrappingData(branch);
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }

    }

    @GetMapping(value = "getBranchAll/{pageNum}/{pageSize}")
    public Map findBranchAll(@PathVariable int pageNum,@PathVariable int pageSize){
        try {
            return super.doWrappingData(organizationService.findBranchAll(pageNum,pageSize));
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }

    }

    @PutMapping(value = "updateBranchById")
    public Map updateBranchById(@ModelAttribute Branch branch){
        try {
         /*  LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchName",branchName);
            parameterMap.put("theStatus",theStatus);
            parameterMap.put("serial",serial);*/
          /*   Branch branch= organizationService.updateBranchBySerial(parameterMap);
            return super.doWrappingData(branch);*/
          /*  if(StringUtils.isEmpty(branch.getBranchName())){
                throw new Exception("请检查参数字段。该机构名称字段必填，非空！");
            }*/
            int rowCount=organizationService.updateBranchById(branch);
            if(rowCount>0){
                branch=organizationService.findBranchBySerialOrId(branch.getSerial(),branch.getId());
                return  super.doWrappingData(branch);
            } else {
                throw new Exception("更新机构失败！");
            }

        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }

    }

    @GetMapping(value = "findBranchParameterAll")
    public Map findBranchParameterAll() {
        try {
            return super.doWrappingData(organizationService.findBranchParameterAll());
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
    @GetMapping(value = "findBranchParameterValueAll/{parameterId}")
    public Map findBranchParameterValueAll(@PathVariable  Integer parameterId,HttpServletRequest request) {
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("parameterId",parameterId);
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            return super.doWrappingData(organizationService.findBranchParameterValueAll(parameterMap));
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }

}
