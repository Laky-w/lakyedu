package com.laky.edu.organization.web;

import com.laky.edu.organization.bean.Branch;
import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.SchoolZoneService;
import com.laky.edu.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
            int rowIndex = schoolZoneService.insertSchoolZoneDao(schoolZone);
            if(rowIndex>0){
                return super.doWrappingData(schoolZone);
            } else {
                throw new Exception("添加校区失败");
            }
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping(value = "/findSchoolZoneAll/{theType}")
    public Map findSchoolZoneAllByBranchId(HttpServletRequest request,@PathVariable  Integer theType) {
        try {
            User user =super.getCurrentUser(request);
            SchoolZone schoolZone = schoolZoneService.querySchoolZoneAllBySchoolZoneId(user.getBranchId(),user.getSchoolZoneId(),theType);//查询用户校区
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
            SchoolZone schoolZone = schoolZoneService.querySchoolZoneAllBySchoolZoneId(user.getBranchId(),schoolId,theType);//查询用户校区
            return  super.doWrappingData(schoolZone);
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping(value = "/findSchoolZoneDaoById/{id}")
    public Map findSchoolZoneDaoById(@PathVariable Integer id) throws Exception {
        try {
            SchoolZone schoolZone = schoolZoneService.querySchoolZoneDaoById(id);
            return  super.doWrappingData(schoolZone);
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }

    @PutMapping(value = "/updateSchoolZone")
    public Map updateSchoolZone(SchoolZone schoolZone) throws Exception {
        try {
            int rowIndex = schoolZoneService.updateSchoolZone(schoolZone);
            if(rowIndex>0){
                return super.doWrappingData(schoolZone);
            } else {
                throw new Exception("添加校区失败");
            }
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
}
