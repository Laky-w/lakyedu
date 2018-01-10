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

    int updateByPrimaryKeySelective(Contact record)throws  Exception;

}