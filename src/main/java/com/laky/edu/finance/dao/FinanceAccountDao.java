package com.laky.edu.finance.dao;

import com.laky.edu.finance.bean.FinanceAccount;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface FinanceAccountDao {

    int insert(FinanceAccount record);

    Map selectFinanceAccount(LinkedHashMap parameterMap);

    List<FinanceAccount> selectByParameterMap(LinkedHashMap parameterMap);

    int updateFinanceAccount(FinanceAccount financeAccount);

    int batchUpdateByPrimaryKeySelective(List<FinanceAccount> record);

    int updateByPrimaryKey(FinanceAccount record);
}