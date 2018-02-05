package com.laky.edu.organization.web;

import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.Branch;
import com.laky.edu.organization.bean.BranchParameterValue;
import com.laky.edu.organization.bean.Role;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.OrganizationService;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 95 on 2017/11/14.
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleService roleService;

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



    @PostMapping(value = "createBranchParameterValue")
    public Map createBranchParameterValue(HttpServletRequest request,  BranchParameterValue branchParameterValue){
        try {
            branchParameterValue.setBranchId(getCurrentUser(request).getBranchId());
            branchParameterValue.setCreateUserId(getCurrentUser(request).getId());
            super.handleOperate("增加机构参数", OrganizationConst.OPERATE_ADD,"增加机构参数值["+branchParameterValue.getName()+"]",request);
            return super.doWrappingData(organizationService.createBranchParameterValue(branchParameterValue));
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

    @GetMapping(value = "findBranchParameterByType/{theType}")
    public Map findBranchParameterByType(@PathVariable Integer theType) {
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("theType",theType);
            return super.doWrappingData(organizationService.findBranchParameterByType(parameterMap));
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

    @PostMapping(value = "createNewRole")
    public Map createNewRole(HttpServletRequest request,@RequestParam(required = true) String name,@RequestParam String remarks,Integer id,@RequestParam(required = true)Integer [] authorities){
        try {
            if(id !=null){
                Role role = new Role();
                role.setId(id);
                role.setName(name);
                role.setRemarks(remarks);
                role=roleService.updateRole(role,authorities);
                return super.doWrappingData(role);
            } else {
                //初始化权限数据
                Role role = new Role();
                User user = getCurrentUser(request);
                role.setBranchId(user.getBranchId());
                role.setSchoolId(user.getSchoolZoneId());
                role.setCreateUserId(user.getId());
                role.setName(name);
                role.setRemarks(remarks);
                role=roleService.createRole(role,authorities);
                super.handleOperate("增加职能",OrganizationConst.OPERATE_ADD,"增加职能【"+role.getName()+"】",request);
                return super.doWrappingData(role);
            }

        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping(value = "deleteRole/{id}")
    public Map deleteRole(HttpServletRequest request,@PathVariable Integer id){
        try {
            //初始化权限数据
            Role role  = roleService.findRoleById(id);
            boolean flag = roleService.deleteRole(id);
            if(flag){
                super.handleOperate("删除职能",OrganizationConst.OPERATE_DELETE,"删除职能【"+role.getName()+"】",request);
            } else {
                throw new Exception("删除职能失败！");
            }
            return super.doWrappingData(role);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping(value = "getRoleDetail/{roleId}")
    public Map getRoleDetail(HttpServletRequest request,@PathVariable  Integer roleId){
        try {
            //初始化权限数据
            Role role = roleService.findRoleById(roleId);
            return super.doWrappingData(role);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
}
