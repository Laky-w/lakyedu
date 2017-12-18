package com.laky.edu.finance.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.FinanceAccount;
import com.laky.edu.finance.dao.FinanceAccountDao;
import com.laky.edu.finance.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by 湖之教育工作室·laky on 2017/12/18.
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceAccountDao financeAccountDao;

    @Override
    public PageBean<FinanceAccount> findFinanceAccountAllBySchool(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(financeAccountDao.selectByParameterMap(parameterMap));
    }

    @Override
    public FinanceAccount createFinanceAccount(FinanceAccount financeAccount) throws Exception {
        financeAccount.setTheStatus(1);
        financeAccount.setTheTime(new Date());
        int rows=financeAccountDao.insert(financeAccount);
        if(rows==0) throw new Exception("创建校区账户失败");
        return financeAccount;
    }
}
