package com.laky.edu.finance.dao;

import com.laky.edu.finance.bean.MoneyRecord;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface MoneyRecordDao {

    int insert(MoneyRecord record);

    MoneyRecord selectById(Integer id);

    Map selectMoneyRecordById(LinkedHashMap parameterMap);

    List<MoneyRecord> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(MoneyRecord record);

    int batchUpdateByPrimaryKeySelective(List<MoneyRecord> record);

    int updateByPrimaryKey(MoneyRecord record);
}