package com.laky.edu.reception.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.MoneyRecord;
import com.laky.edu.finance.bean.MoneyRecordAccount;
import com.laky.edu.finance.dao.MoneyRecordAccountDao;
import com.laky.edu.finance.dao.MoneyRecordDao;
import com.laky.edu.reception.ReceptionConst;
import com.laky.edu.reception.bean.StudentOrder;
import com.laky.edu.reception.bean.StudentOrderDetail;
import com.laky.edu.reception.dao.StudentOrderDao;
import com.laky.edu.reception.dao.StudentOrderDetailDao;
import com.laky.edu.reception.service.OrderService;
import com.laky.edu.reception.web.form.OrderMoneyRecordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/12/28.
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private StudentOrderDao orderDao;

    @Autowired
    private StudentOrderDetailDao orderDetailDao;

    @Autowired
    private MoneyRecordDao moneyRecordDao;

    @Autowired
    private MoneyRecordAccountDao moneyRecordAccountDao;

    @Override
    public PageBean<StudentOrder> findStudentOrderAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(orderDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public StudentOrder createOrderMoneyRecord(OrderMoneyRecordForm moneyRecordForm) throws Exception {
        StudentOrder order = orderDao.selectById(moneyRecordForm.getId());
        //生成收支记录
        MoneyRecord record = new MoneyRecord();
        record.setMoney(moneyRecordForm.getMoney());
        record.setSalesmanId(moneyRecordForm.getSalesmanId());
        record.setCreateTime(new Date());
        record.setTheStatus(1);
        record.setCheckStatus(1); //审核状态
        record.setTheType(1);//收支类型
        record.setSchoolZoneId(order.getSchoolZoneId());
        record.setOrderId(order.getId());
        moneyRecordDao.insert(record);
        List<MoneyRecordAccount> newFinanceAccount = new ArrayList<>();
        MoneyRecordAccount recordAccount = new MoneyRecordAccount();
        recordAccount.setRecordId(record.getId());
        recordAccount.setMoney(moneyRecordForm.getMoney());
        recordAccount.setAccountId(moneyRecordForm.getAccountId());
        newFinanceAccount.add(recordAccount);
        moneyRecordAccountDao.batchInsert(newFinanceAccount);
        order.setSubtractMoney(order.getSubtractMoney().add(moneyRecordForm.getSubtractMoney()));//减免
        order.setReceivable(order.getReceivable().add(moneyRecordForm.getMoney()));//已收
        System.out.println(order.getTotal().subtract(order.getReceivable()).subtract(order.getSubtractMoney()));
        if(order.getTotal().subtract(order.getReceivable()).subtract(order.getSubtractMoney()).compareTo(BigDecimal.ZERO)==0){
            order.setCostStatus(ReceptionConst.ORDER_COST_STATUS_COMPLETE); //已收齐
        }
        orderDao.updateByPrimaryKeySelective(order);
        return order;
    }

    @Override
    public StudentOrder findStudentOrderById(Integer orderId) throws Exception {
        return orderDao.selectById(orderId);
    }

    @Override
    public List<StudentOrderDetail> findStudentOrderDetailByOrderId(LinkedHashMap parameterMap) throws Exception {
        return orderDetailDao.selectByParameterMap(parameterMap);
    }
}
