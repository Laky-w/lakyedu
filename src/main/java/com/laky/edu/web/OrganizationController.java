package com.laky.edu.web;

import com.laky.edu.bean.Branch;
import com.laky.edu.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by 95 on 2017/11/14.
 */
@RestController
public class OrganizationController extends BaseController{

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

}
