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
     * 增加收支流水记录
     * @param moneyRecord
     * @return
     * @throws Exception
     */
    MoneyRecord createMoneyRecord(MoneyRecord moneyRecord)throws Exception;
    /**
     * 审核收支记录
     * @param ids
     * @param checkedStatus
     * @return
     * @throws Exception
     */
    int doUpdateMoneyRecord(String ids,Integer checkedStatus)throws Exception;

    /**
     * 查询收支记录详情
     * @param recordId
     * @return
     * @throws Exception
     */
    MoneyRecord findMoneyRecord(Integer recordId)throws Exception;

    /**
     * 查询收支记录账户明细
     * @param recordId
     * @return
     * @throws Exception
     */
    List<MoneyRecordAccount> findMoneyRecordAccountList(Integer recordId)throws Exception;

    /**
     * 创建财务账户
     * @param financeAccount
     * @return
     * @throws Exception
     */
    FinanceAccount createFinanceAccount(FinanceAccount financeAccount) throws Exception;

    /**
     * 修改财务账户
     * @param financeAccount
     * @return
     * @throws Exception
     */
    FinanceAccount updateFinanceAccount(FinanceAccount financeAccount)throws Exception;

    /**
     * 删除财务账户
     * @param id
     * @return
     * @throws Exception
     */
    int deleteFinanceAccount(Integer id)throws Exception;

    /**
     *查询财务账户详情
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Map selectFinanceAccount(LinkedHashMap parameterMap)throws Exception;
}
