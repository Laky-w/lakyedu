package com.laky.edu.log.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.log.bean.OperateLog;
import com.laky.edu.log.dao.OperateLogDao;
import com.laky.edu.log.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService{
    @Autowired
    private OperateLogDao operateLogDao;
    /**
     * 添加一个操作人日志
     * (#{createTime},#{operatePerson},#{title},#{content},#{theType},#{branch.id},#{accountId})
     * @param operatelog
     * @return
     */
    @Transactional
    @Override
    public int addOperateLog(OperateLog operatelog) throws Exception {
//        operatelog.setContent("日志");
//        operatelog.setCreateTime(new Date());
//        operatelog.setAccountId(operatelog.getAccountId());
//        operatelog.setOperatePerson(operatelog.getOperatePerson());
//        operatelog.setTheType(1);
//        operatelog.setTitle("addUser");
//        operatelog.setSchoolZoneId(operatelog.getSchoolZoneId());
        return operateLogDao.insertOperateLog(operatelog);
    }


    @Override
    public PageBean findLogAllByBranchId(LinkedHashMap parameterMap) throws Exception {
        // PageHelper pageHelper = new PageHelper();
        int pageNum = (int) parameterMap.get("pageNum");
        int pageSize = (int) parameterMap.get("pageSize");
        PageHelper.startPage(pageNum, pageSize);

        return new PageBean(operateLogDao.queryOperateLogAll(parameterMap));
    }

}
