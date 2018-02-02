package com.laky.edu.reception.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.reception.bean.StudentOrder;
import com.laky.edu.reception.bean.StudentOrderDetail;
import com.laky.edu.reception.web.form.OrderMoneyRecordForm;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/12/28.
 */
public interface OrderService {
    /**
     * 查询学生订单
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<StudentOrder> findStudentOrderAll(LinkedHashMap parameterMap)throws Exception;

    /**
     * 添加订单收支记录
     * @param moneyRecordForm
     * @return
     * @throws Exception
     */
    StudentOrder createOrderMoneyRecord(OrderMoneyRecordForm moneyRecordForm) throws Exception;

    /**
     * 查询订单详情
     * @param orderId
     * @return
     * @throws Exception
     */
    StudentOrder findStudentOrderById(Integer orderId)throws Exception;

    /**
     * 查询订单明细
     * @param parameterMap
     * @return
     * @throws Exception
     */
    List<StudentOrderDetail> findStudentOrderDetailByOrderId(LinkedHashMap parameterMap)throws Exception;
}
