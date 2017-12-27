package com.laky.edu.finance.dao;

import com.laky.edu.finance.bean.FinanceAccount;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface FinanceAccountDao {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    List<FinanceAccount> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int batchUpdateByPrimaryKeySelective(List<FinanceAccount> record);

    int updateByPrimaryKey(FinanceAccount record);
}