package com.laky.edu.reception.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.reception.bean.StudentOrder;
import com.laky.edu.reception.bean.StudentOrderDetail;
import com.laky.edu.reception.dao.StudentOrderDao;
import com.laky.edu.reception.dao.StudentOrderDetailDao;
import com.laky.edu.reception.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageBean<StudentOrder> findStudentOrderAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(orderDao.selectByParameterMap(parameterMap));
    }

    @Override
    public List<StudentOrderDetail> findStudentOrderDetailByOrderId(LinkedHashMap parameterMap) throws Exception {
        return orderDetailDao.selectByParameterMap(parameterMap);
    }
}
