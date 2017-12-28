package com.laky.edu.finance.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.FinanceAccount;
import com.laky.edu.finance.bean.MoneyRecord;
import com.laky.edu.finance.bean.MoneyRecordAccount;

import java.util.LinkedHashMap;
import java.util.List;
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

    /**
     * 查询收支记录
     * @param parameterMap
     * @return
     */
    PageBean<MoneyRecord> findMoneyRecordAllBySchool(LinkedHashMap parameterMap) throws Exception;

    /**
     * 查询收支记录账户明细
     * @param recordId
     * @return
     * @throws Exception
     */
    List<MoneyRecordAccount> findMoneyRecordAccountList(Integer recordId)throws Exception;

    FinanceAccount createFinanceAccount(FinanceAccount financeAccount) throws Exception;
}
