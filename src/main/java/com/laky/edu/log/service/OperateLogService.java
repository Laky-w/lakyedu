package com.laky.edu.log.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.log.bean.OperateLog;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


public interface OperateLogService {


    /**
     * 添加一个操作人日志
     * @param operatelog
     * @return
     */
    int addOperateLog(OperateLog operatelog) throws Exception;


    /**
     * 查询全部日志
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean findLogAllByBranchId(LinkedHashMap parameterMap) throws Exception;


    /**
     * 删除过期日志通过时间
     * @param createTime
     * @return
     */
    int deleteExpireLogByCreatetime(Date createTime);
}
