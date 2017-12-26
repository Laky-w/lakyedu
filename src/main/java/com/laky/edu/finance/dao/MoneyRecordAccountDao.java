package com.laky.edu.finance.dao;

import com.laky.edu.finance.bean.MoneyRecordAccount;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface MoneyRecordAccountDao {

    int insert(MoneyRecordAccount record);

    List<MoneyRecordAccount> selectByParameter(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(MoneyRecordAccount record);

    int updateByPrimaryKey(MoneyRecordAccount record);
}