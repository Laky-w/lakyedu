package com.laky.edu.reception.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.reception.bean.StudentOrder;
import com.laky.edu.reception.bean.StudentOrderDetail;

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

    List<StudentOrderDetail> findStudentOrderDetailByOrderId(LinkedHashMap parameterMap)throws Exception;
}
