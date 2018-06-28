package com.laky.edu.organization.web;

import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.Branch;
import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.SchoolZoneService;
import com.laky.edu.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 95 on 2017/11/20.
 */
@RestController
@RequestMapping("/organization")
public class SchoolZoneController extends BaseController {
    @Autowired
    private SchoolZoneService schoolZoneService;

    @PostMapping(value = "/createSchoolZone")
    public Map createSchoolZone(SchoolZone schoolZone,HttpServletRequest request) {
        try {
            Branch branch = new Branch();
            branch.setId(super.getCurrentUser(request).getBranchId());
            schoolZone.setBranch(branch);
            if(schoolZone.getId()==null){
                int rowIndex = schoolZoneService.insertSchoolZoneDao(schoolZone);
                if(rowIndex>0){
                    handleOperate("新增校区", OrganizationConst.OPERATE_ADD,"添加校区【"+schoolZone.getName()+"】",request);
                    return super.doWrappingData(schoolZone);
                } else {
                    throw new Exception("添加校区失败");
                }
            } else {
                int rowIndex = schoolZoneService.updateSchoolZone(schoolZone);
                if(rowIndex>0){
                    handleOperate("修改校区", OrganizationConst.OPERATE_UPDATE,"修改校区【"+schoolZone.getName()+"】",request);
                    return super.doWrappingData(schoolZone);
                } else {
                    throw new Exception("修改校区失败");
                }
            }
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }



    @GetMapping(value = "/getSchoolZoneView/{id}")
    public Map getSchoolZoneView(HttpServletRequest request,@PathVariable Integer id) {
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            parameterMap.put("branchId",getCurrentUser(request).getBranchId());
            Map map =schoolZoneService.findSchoolZone(parameterMap);
            if(!map.get("theType").equals("3")){ //不是部门
                Integer[] ids= super.getSchoolIds(request,2,map.get("id"));
                if(ids !=null) map.put("schoolNumber",ids.length-1);
                else map.put("schoolNumber",0);
            }
            return super.doWrappingData( map);
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping(value = "/findSchoolZoneAll/{theType}")
    public Map findSchoolZoneAllByBranchId(HttpServletRequest request,@PathVariable  Integer theType) {
        try {
            User user =super.getCurrentUser(request);
            //查询用户校区
            SchoolZone schoolZone = schoolZoneService.querySchoolZoneAllBySchoolZoneId(user.getBranchId(),user.getSchoolZoneId(),theType);
            return  super.doWrappingData(schoolZone);
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
    @GetMapping(value = "/findChildSchoolZoneAll/{theType}/{schoolId}")
    public Map findChildSchoolZoneAllByBranchId(HttpServletRequest request,@PathVariable  Integer theType,@PathVariable Integer schoolId) {
        try {
            User user =super.getCurrentUser(request);
            if(schoolId == 0) {
                schoolId =user.getSchoolZoneId();
            }
            //查询用户校区
            SchoolZone schoolZone = schoolZoneService.querySchoolZoneAllBySchoolZoneId(user.getBranchId(),schoolId,theType);
            return  super.doWrappingData(schoolZone);
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }

    @DeleteMapping(value = "deleteSchoolZone/{schoolId}")
    public Map deleteSchoolZone(HttpServletRequest request,@PathVariable Integer schoolId){
        try {
            User user =super.getCurrentUser(request);
            if(schoolId == 0) {
                schoolId =user.getSchoolZoneId();
            }
            String  log= schoolZoneService.deleteSchoolZone(schoolId,super.getCurrentUser(request).getBranchId());
            handleOperate("删除校区",OrganizationConst.OPERATE_DELETE,log,request);
            return  super.doWrappingData("删除成功");
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
    @PutMapping(value = "normalSchoolZone/{schoolId}")
    public Map normalSchoolZone(HttpServletRequest request,@PathVariable Integer schoolId){
        try {
            User user =super.getCurrentUser(request);
            if(schoolId == 0) {
                schoolId =user.getSchoolZoneId();
            }
            String  log= schoolZoneService.normalSchoolZone(schoolId,super.getCurrentUser(request).getBranchId());
            handleOperate("启用校区",OrganizationConst.OPERATE_DELETE,log,request);
            return  super.doWrappingData("启用成功");
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
    @PutMapping(value = "sealUpSchoolZone/{schoolId}")
    public Map sealUpSchoolZone(HttpServletRequest request,@PathVariable Integer schoolId){
        try {
            User user =super.getCurrentUser(request);
            if(schoolId == 0) {
                schoolId =user.getSchoolZoneId();
            }
            String  log= schoolZoneService.sealUpSchoolZone(schoolId,super.getCurrentUser(request).getBranchId());
            handleOperate("封存校区",OrganizationConst.OPERATE_DELETE,log,request);
            return  super.doWrappingData("封存成功");
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
}
