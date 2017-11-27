package com.laky.edu.log.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.log.bean.LoginLog;
import com.laky.edu.log.service.LoginLogService;
import com.laky.edu.organization.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class LoginLogController extends BaseController{

    @Autowired
    private LoginLogService loginLogService;
    /**
     * 获取登录日志
     * @return
     */
    @PostMapping("findLoginLog/{pageNum}/{pageSize}")
    public Map findLoginLog(HttpServletRequest request,
                            @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            User user =userSession.get(request.getHeader("token"));
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolZoneId",user.getSchoolZoneId());
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
//            Date date=new Date(request.getParameter("theDate1"));

            parameterMap=super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(loginLogService.selectByParameter(parameterMap));
        } catch (Exception e) {
            return super.doWrappingErrorData(e);
        }
    }
}