package com.laky.edu.organization.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.core.PageBean;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.Notice;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organization")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping(value = "createNotice")
    public  Map createNotice(HttpServletRequest request,Notice notice){
        try {
            notice.setBranchId(getCurrentUser(request).getBranchId());
            notice.setSchoolZoneId(getCurrentUser(request).getSchoolZoneId());
            notice.setUserId(getCurrentUser(request).getId());

            int rowCount= noticeService.createNotice(notice);
            if(rowCount>0){
                super.handleOperate("添加公告", OrganizationConst.OPERATE_ADD,"公告内容【"+notice.getContent()+"】",request);
                return  super.doWrappingData(notice);
            } else {
                throw new Exception("创建公告失败！");
            }
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }
    @GetMapping(value = "/findNewNoticeAll")
    public Map findNewNoticeAll(HttpServletRequest request){
        try {
            // 系统给的通知,当前校区给的通知,当前机构给的通知
            User user = getCurrentUser(request);
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",user.getBranchId());
            parameterMap.put("schoolZoneId",user.getSchoolZoneId());
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(noticeService.findNewNotice(parameterMap));
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }

    }

    @GetMapping(value = "/findNoticeAll")
    public Map findNoticeAll(HttpServletRequest request){
        try {
            // 系统给的通知,当前校区给的通知,当前机构给的通知

            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",getCurrentUser(request).getBranchId());
            parameterMap.put("schoolZoneId",getCurrentUser(request).getSchoolZoneId());
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(noticeService.findNewNotice(parameterMap));
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }

    }

    @PostMapping (value = "/findNoticeAll/{pageNum}/{pageSize}")
    public Map findNoticeAll(HttpServletRequest request,@PathVariable int pageNum,@PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",getCurrentUser(request).getBranchId());
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap.put("schoolZoneId",getSchoolIds(request));
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(noticeService.findNoticeByBranchOrSchool(parameterMap));
        } catch (Exception e){
            return  super.doWrappingErrorData(e);
        }

    }
}
