package com.laky.edu.supply.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.dao.CustomerDao;
import com.laky.edu.supply.service.CustomerService;
import com.laky.edu.util.PinYinUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        customer.setTheType(1);
        customer.setInviteStatus(1);
        customer.setContactStatus(1);
        if(!StringUtils.isEmpty(customer.getOwnerId())){
            customer.setAllotStatus(2);//已分配
        } else {
            customer.setAllotStatus(1);//未分配
        }
        customer.setCreateTime(new Date());
        //PinyinHelper.
        customer.setPinyin(PinYinUtil.parsePinYin(customer.getName()));
        int rows=customerDao.insertCustomer(customer);
        if(rows==0) throw new Exception("创建生源失败！");
        if(null !=intentionIds){ //意向课程添加
            customerDao.insertIntentionCourse(getIntentionCourseList(intentionIds,customer.getId()));
        }
        return customer;

    }

    @Transactional
    @Override
    public Customer updateCustomer(Customer customer, Integer[] intentionIds) throws Exception {
        if(!StringUtils.isEmpty(customer.getOwnerId())){
            customer.setAllotStatus(2);//已分配
        } else {
            customer.setAllotStatus(1);//未分配
        }
        customer.setPinyin(PinYinUtil.parsePinYin(customer.getName()));
        int rows=customerDao.updateByPrimaryKeySelective(customer);
        if(rows==0) throw new Exception("修改生源失败！");
        if(null !=intentionIds){ //意向课程添加
            //删除历史意向课程
            customerDao.deleteIntentionCourseByStudentId(customer.getId());
            customerDao.insertIntentionCourse(getIntentionCourseList(intentionIds,customer.getId()));
        }
        return customer;
    }

    @Transactional
    @Override
    public boolean deleteCustomer(Integer customerId) throws Exception {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setTheStatus(0);
        int rowCount = customerDao.updateByPrimaryKeySelective(customer);
        return !(rowCount==0);
    }

    private  List<Map> getIntentionCourseList(Integer[] intentionIds, Integer customerId){
        List<Map> intentionCourseList = new ArrayList<>();
        for (Integer intentionId: intentionIds) {
            Map map = new HashMap<>();
            map.put("courseId",intentionId);
            map.put("studentId",customerId);
            intentionCourseList.add(map);
        }
        return intentionCourseList;
    }

    @Override
    public Map findCustomer(LinkedHashMap parameterMap) throws Exception {
        return customerDao.selectCustomer(parameterMap);
    }

    @Override
    public PageBean<Customer> findCustomerAll(LinkedHashMap parameterMap) throws Exception {

        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(customerDao.selectByParameterMap(parameterMap));
    }




}
