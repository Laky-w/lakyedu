package com.laky.edu.finance.dao;

import com.laky.edu.finance.bean.MoneyRecord;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface MoneyRecordDao {

    int insert(MoneyRecord record);

    List<MoneyRecord> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(MoneyRecord record);

    int updateByPrimaryKey(MoneyRecord record);
}