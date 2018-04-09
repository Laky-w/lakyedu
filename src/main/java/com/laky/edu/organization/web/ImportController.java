package com.laky.edu.organization.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/3/17.
 */
@RestController
@RequestMapping("/organization")
public class ImportController extends BaseController{

    @Autowired
    private ImportService importService;

    @PostMapping("/getImportRecordList/{pageNum}/{pageSize}")
    public Map getImportRecordList(HttpServletRequest request, @PathVariable int pageNum,@PathVariable  int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap.put("schoolZoneId",super.getSchoolIds(request, OrganizationConst.SCHOOL_ZONE_TYPE_CHILD));
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return  super.doWrappingData(importService.findImportRecord(parameterMap));
        }catch (Exception e){
            return super.doWrappingErrorData(e);
        }
    }
}
