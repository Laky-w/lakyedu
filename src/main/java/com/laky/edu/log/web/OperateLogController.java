package com.laky.edu.log.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.log.bean.OperateLog;
import com.laky.edu.log.service.OperateLogService;
import com.laky.edu.organization.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class OperateLogController extends BaseController{
    @Autowired
    private OperateLogService operateLogService;


    /**
     * 查询所有的操作日志根据机构id
     * @param
     * @return
     */
    @PostMapping(value = "/findOperateLogAll/{pageNum}/{pageSize}")
    public Map findOperateLogAll(HttpServletRequest request,
                                 @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",getCurrentUser(request).getBranchId());
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(operateLogService.findLogAllByBranchId(parameterMap));
        }catch ( Exception e ){
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping(value = "/addOperateLog")
    public Map addOperateLog(OperateLog operatelog){
        try {
            int rowIndex = operateLogService.addOperateLog(operatelog);
            if(rowIndex>0){
                return super.doWrappingData(operatelog);
            } else {
                throw new Exception("添加日志失败");
            }


        }catch ( Exception e ){
            return  super.doWrappingErrorData(e);
        }

    }
    @GetMapping("/deleteExpireLogByCreatetime")
    public void deleteExpireLogByCreatetime(Date createTime) {
        try {
            OperateLog operateLog = new OperateLog();
            long day=0;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date beginDate= operateLog.getCreateTime() ;
            Date endDate =  new Date(System.currentTimeMillis());
            beginDate = format.parse(String.valueOf(beginDate));
            endDate = format.parse(String.valueOf(endDate));
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            if (day>1){
                operateLogService.deleteExpireLogByCreatetime(createTime);
            }

             }catch ( Exception e ){

            }


    }



}
