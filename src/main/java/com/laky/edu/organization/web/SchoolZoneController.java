package com.laky.edu.organization.web;

import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.service.SchoolZoneService;
import com.laky.edu.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Map createSchoolZone(SchoolZone schoolZone) {
        try {
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

    @GetMapping(value = "/findSchoolZoneAllByBranchId/{branchId}")
    public Map findSchoolZoneAllByBranchId(@PathVariable Integer branchId) {
        try {
            List<SchoolZone> dataList = schoolZoneService.querySchoolZoneAllByBranchId(branchId);
            return  super.doWrappingData(dataList);
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
