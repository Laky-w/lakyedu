package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Contact;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

public interface ContactService {
    /**
     * 增加联系记录
     * @param contact
     * @return
     * @throws Exception
     */
    Contact createContact(Contact contact) throws Exception;

    /**
     *查询所有联系人
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<Contact> findContactAll(LinkedHashMap parameterMap)throws Exception;

    /**
     * 查询联系记录详情
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Contact findContactById(LinkedHashMap parameterMap ) throws Exception;

    int updateByPrimaryKeySelective(Contact record)throws  Exception;

    /**
     * 删除联系记录
     * @param ids
     * @return
     * @throws Exception
     */
    int deleteContact(String [] ids) throws Exception;

}