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

    /**
     * 查询操作时间段日志通过create_time
     * @param createTime
     * @return
     */
    OperateLog queryOperateLogByCreateTime(Date createTime);


    /**
     * 删除过期日志通过时间
     * @param createTime
     * @return
     */
    int deleteExpireLogByCreatetime(Date createTime);


    int deleteByPrimaryKey(Integer id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    List<OperateLog> selectByPrimaryKey(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKeyWithBLOBs(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
}
