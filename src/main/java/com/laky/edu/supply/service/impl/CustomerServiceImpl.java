package com.laky.edu.supply.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.dao.CustomerDao;
import com.laky.edu.supply.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public int deleteByPrimaryKey(Integer id) throws Exception {
        return 0;
    }
    @Transactional
    @Override
    public Customer createCustomer(Customer customer,Integer [] intentionIds) throws Exception {
        customer.setTheStatus(1);
        customer.setTheType(1);//生源类型处理
        customer.setCreateTime(new Date());
        int rows=customerDao.insertCustomer(customer);
        if(rows==0) throw new Exception("创建生源失败！");
        if(null !=intentionIds){ //意向课程添加
            List<Map> intentionCourseList = new ArrayList<>();
            for (Integer intentionId: intentionIds) {
                Map map = new HashMap<>();
                map.put("courseId",intentionId);
                map.put("studentId",customer.getId());
                intentionCourseList.add(map);
            }
            customerDao.insertIntentionCourse(intentionCourseList);
        }
        return customer;

    }

    @Override
    public Customer createSelective(Customer customer) throws Exception {
        return null;
    }

    @Override
    public PageBean<Customer> findCustomerAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(customerDao.selectByParameterMap(parameterMap));
    }

    @Override
    public int updateByPrimaryKeySelective(Customer reccustomerord) throws Exception {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Customer customer) throws Exception {
        return 0;
    }
}
