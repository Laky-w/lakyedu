package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Customer;

import java.util.LinkedHashMap;
import java.util.List;

public interface CustomerService {
    int deleteByPrimaryKey(Integer id)throws Exception;

    Customer createCustomer(Customer customer)throws Exception;

    Customer createSelective(Customer customer)throws Exception;

    PageBean<Customer> findCustomerAll(LinkedHashMap parameterMap)throws Exception;

    int updateByPrimaryKeySelective(Customer reccustomerord)throws Exception;

    int updateByPrimaryKey(Customer customer)throws Exception;
}