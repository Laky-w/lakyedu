package com.laky.edu.supply.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Contact;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.dao.ContactDao;
import com.laky.edu.supply.dao.CustomerDao;
import com.laky.edu.supply.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private CustomerDao customerDao;


    @Transactional
    @Override
    public Contact createContact(Contact contact) throws Exception {
        contact.setTheStatus(1);
        int rows=contactDao.insert(contact);
        if(rows==0)throw new Exception("创建跟进记录失败！");
        Customer customer=customerDao.selectByPrimaryKey(contact.getStudentId());
        customer.setLastContactTime(contact.getLastContactTime());
        customer.setIntentionLevel(contact.getIntentionLevel());
//        customer.setInviteStatus(2);
        customer.setContactStatus(2);//沟通状态
        customerDao.updateByPrimaryKeySelective(customer);
        return contact;
    }



    @Override
    public PageBean<Contact> findContactAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<Contact>(contactDao.selectByParameterMap(parameterMap));
    }

    @Override
    public int updateByPrimaryKeySelective(Contact record) throws Exception {
        return 0;
    }


}
