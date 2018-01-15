package com.laky.edu.log.dao;

import com.laky.edu.log.bean.OperateLog;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface OperateLogDao {


    /**
     * 添加一个操作人日志
     * @param operatelog
     * @return
     */
    int insertOperateLog(OperateLog operatelog);

    /**
     * 查询所有的操作日志根据机构id
     * @param parameterMap
     * @return
     */
    List<OperateLog> queryOperateLogAll(LinkedHashMap parameterMap);


}
