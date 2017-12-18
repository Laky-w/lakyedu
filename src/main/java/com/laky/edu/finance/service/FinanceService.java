package com.laky.edu.finance.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.FinanceAccount;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/18.
 */
public interface FinanceService {
    /**
     * 查询财务账户
     * @param parameterMap
     * @return
     */
    PageBean<FinanceAccount> findFinanceAccountAllBySchool(LinkedHashMap parameterMap) throws Exception;

    FinanceAccount createFinanceAccount(FinanceAccount financeAccount) throws Exception;
}
