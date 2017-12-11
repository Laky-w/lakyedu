package com.laky.edu.supply.dao;

import com.laky.edu.supply.bean.Customer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface CustomerDao {
    int deleteByPrimaryKey(Integer id);

    int insertCustomer(Customer customer);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    List<Customer> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}