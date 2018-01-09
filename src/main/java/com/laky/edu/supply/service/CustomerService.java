package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Customer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface CustomerService {
    int deleteByPrimaryKey(Integer id)throws Exception;

    /**
     * 创建一个生源
     * @param customer
     * @param intentionIds
     * @return
     * @throws Exception
     */
    Customer createCustomer(Customer customer,Integer [] intentionIds)throws Exception;

    /**
     * 修改生源
     * @param customer
     * @param intentionIds
     * @return
     * @throws Exception
     */
    Customer updateCustomer(Customer customer,Integer [] intentionIds)throws Exception;

    /**
     * 生源详情页
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Map findCustomer(LinkedHashMap parameterMap)throws Exception;

    PageBean<Customer> findCustomerAll(LinkedHashMap parameterMap)throws Exception;

    int updateByPrimaryKeySelective(Customer reccustomerord)throws Exception;

    int updateByPrimaryKey(Customer customer)throws Exception;
}