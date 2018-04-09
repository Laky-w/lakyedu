package com.laky.edu.supply.dao;

import com.laky.edu.supply.bean.Customer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface CustomerDao {
    int deleteByPrimaryKey(Integer id);

    int insertCustomer(Customer customer);

    /**
     * 批量保存生源
     * @param customer
     * @return
     */
    int batchInsertCustomer(List<Customer> customer);

    /**
     * 创建意向课程
     * @param intentionCourseList
     * @return
     */
    int insertIntentionCourse(List<Map> intentionCourseList);

    /**
     * 删除意向课程
     * @param studentId
     * @return
     */
    int deleteIntentionCourseByStudentId(Integer studentId);

    Customer selectByPrimaryKey(Integer id);

    Map selectCustomer(LinkedHashMap parameterMap);

    List<Customer> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(List<Customer> record);

    int updateByPrimaryKey(Customer record);


}