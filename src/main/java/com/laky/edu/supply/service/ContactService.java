package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Contact;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

public interface ContactService {
    Contact createContact(Contact contact) throws Exception;

    PageBean<Contact> findContactAll(LinkedHashMap parameterMap)throws Exception;

    int updateByPrimaryKeySelective(Contact record)throws  Exception;

    int updateByPrimaryKeyWithBLOBs(Contact record)throws Exception;

    int updateByPrimaryKey(Contact record)throws Exception;
}