package com.laky.edu.finance.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.FinanceAccount;
import com.laky.edu.finance.bean.MoneyRecord;
import com.laky.edu.finance.bean.MoneyRecordAccount;
import com.laky.edu.finance.dao.FinanceAccountDao;
import com.laky.edu.finance.dao.MoneyRecordAccountDao;
import com.laky.edu.finance.dao.MoneyRecordDao;
import com.laky.edu.finance.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2017/12/18.
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceAccountDao financeAccountDao;

    @Autowired
    private MoneyRecordDao moneyRecordDao;

    @Autowired
    private MoneyRecordAccountDao moneyRecordAccountDao;

    @Override
    public PageBean<FinanceAccount> findFinanceAccountAllBySchool(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int) parameterMap.get("pageNum"), (int) parameterMap.get("pageSize"));
        return new PageBean<>(financeAccountDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public MoneyRecord createMoneyRecord(MoneyRecord moneyRecord, Integer accountId) throws Exception {
        moneyRecord.setCheckStatus(2);
        moneyRecord.setCreateTime(new Date());
        moneyRecord.setTheStatus(1);
        moneyRecord.setTheType(1);
        moneyRecord.setBillType(2);
        int rows = moneyRecordDao.insert(moneyRecord);
        if (rows == 0) throw new RuntimeException("创建收支流水失败!");
        List<MoneyRecordAccount> newFinanceAccount = new ArrayList<>();
        MoneyRecordAccount recordAccount = new MoneyRecordAccount();
        recordAccount.setRecordId(moneyRecord.getId());
        recordAccount.setMoney(moneyRecord.getMoney());
        recordAccount.setAccountId(accountId);
        newFinanceAccount.add(recordAccount);
        moneyRecordAccountDao.batchInsert(newFinanceAccount);
        return moneyRecord;
    }
    @Transactional
    @Override
    public int deleteMoneyRecord(Integer id) throws Exception {
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setId(id);
        moneyRecord.setTheStatus(0);
        int rows = moneyRecordDao.updateByPrimaryKeySelective(moneyRecord);
        if (rows ==0)throw new RuntimeException("删除收支流水失败！");
        return rows;
    }
    @Transactional
    @Override
    public MoneyRecord updateMoneyRecord(MoneyRecord moneyRecord) throws Exception {
        int rows = moneyRecordDao.updateByPrimaryKeySelective(moneyRecord);
        if (rows==0)throw new RuntimeException("修改收支流水失败！");
        return moneyRecord;
    }

    @Override
    public Map findMoneyRecordById(LinkedHashMap parameterMap) throws Exception {
        return moneyRecordDao.selectMoneyRecordById(parameterMap);
    }

    @Override
    public PageBean<MoneyRecord> findMoneyRecordAllBySchool(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int) parameterMap.get("pageNum"), (int) parameterMap.get("pageSize"));
        return new PageBean<>(moneyRecordDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public int doUpdateMoneyRecord(String ids, Integer checkedStatus) throws Exception {
        String[] recordIds = ids.split(",");
        List<MoneyRecord> moneyRecordList = new ArrayList<>();
        for (String id : recordIds) {
            MoneyRecord moneyRecord = new MoneyRecord();
            moneyRecord.setId(Integer.parseInt(id));
            moneyRecord.setCheckStatus(checkedStatus);
            moneyRecordList.add(moneyRecord);
        }
        return moneyRecordDao.batchUpdateByPrimaryKeySelective(moneyRecordList);
    }

    @Override
    public MoneyRecord findMoneyRecord(Integer recordId) throws Exception {
        return moneyRecordDao.selectById(recordId);
    }

    @Override
    public List<MoneyRecordAccount> findMoneyRecordAccountList(Integer recordId) throws Exception {
        return moneyRecordAccountDao.selectByParameter(recordId);
    }

    @Transactional
    @Override
    public FinanceAccount createFinanceAccount(FinanceAccount financeAccount) throws Exception {
        financeAccount.setTheStatus(1);
        financeAccount.setTheTime(new Date());
        if (null == financeAccount.getMoney()) financeAccount.setMoney(BigDecimal.ZERO);
        int rows = financeAccountDao.insert(financeAccount);
        if (rows == 0) throw new Exception("创建校区账户失败");
        return financeAccount;
    }

    @Transactional
    @Override
    public FinanceAccount updateFinanceAccount(FinanceAccount financeAccount) throws Exception {
        int rows = financeAccountDao.updateFinanceAccount(financeAccount);
        if (rows == 0) throw new RuntimeException("更新财务账户失败!");
        return financeAccount;
    }

    @Transactional
    @Override
    public int deleteFinanceAccount(Integer id) throws Exception {
        FinanceAccount financeAccount = new FinanceAccount();
        financeAccount.setId(id);
        financeAccount.setTheStatus(0);
        int rows = financeAccountDao.updateFinanceAccount(financeAccount);
        if (rows == 0) throw new RuntimeException("删除财务账户失败!");
        return rows;
    }

    @Override
    public Map selectFinanceAccount(LinkedHashMap parameterMap) throws Exception {
        return financeAccountDao.selectFinanceAccount(parameterMap);
    }
}
