package com.laky.edu.organization.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.organization.bean.Notice;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organization")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;


    @GetMapping(value = "findNoticeAll")
    public Map findNoticeAll(HttpServletRequest request){
        try {
            // 系统给的通知,当前校区给的通知,当前机构给的通知
            User user = getCurrentUser(request);
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",user.getBranchId());
            parameterMap.put("schoolId",user.getSchoolZoneId());
            List<Notice> dataList =noticeService.findByBranchOrSchool(parameterMap);
            return super.doWrappingData(dataList);
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }

    }
}
