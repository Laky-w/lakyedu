package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface CustomerService {


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
     * 删除生源
     * @param customerId
     * @return
     * @throws Exception
     */
    boolean deleteCustomer(Integer customerId)throws Exception;

    /**
     * 生源详情页
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Map findCustomer(LinkedHashMap parameterMap)throws Exception;

    /**
     * 查询全部生源
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<Customer> findCustomerAll(LinkedHashMap parameterMap)throws Exception;

    /**
     * 分配生源
     * @param ownerId
     * @param students
     * @return
     * @throws Exception
     */
    int updateUserOwner(Integer ownerId,Integer [] students) throws Exception;

    /**
     * 导入生源
     * @param schoolId 校区id
     * @param ownerId 负责人id
     * @param file 导入文件
     * @return
     * @throws Exception
     */
    void importCustomer(Integer schoolId,Integer ownerId,MultipartFile file) throws Exception;
}