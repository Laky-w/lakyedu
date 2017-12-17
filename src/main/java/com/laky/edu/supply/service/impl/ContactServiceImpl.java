package com.laky.edu.supply.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Contact;
import com.laky.edu.supply.dao.ContactDao;
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

    /**
     * 增加联系人
     * @param contact
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Contact createContact(Contact contact) throws Exception {
        contact.setTheStatus(1);
        int rows=contactDao.insert(contact);
        if(rows==0)throw new Exception("创建联系人失败！");
        return contact;
    }


    /**
     *查询所有联系人
     * @param parameterMap
     * @return
     * @throws Exception
     */
    @Override
    public PageBean<Contact> findContactAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<Contact>(contactDao.selectByParameterMap(parameterMap));
    }

    @Override
    public int updateByPrimaryKeySelective(Contact record) throws Exception {
        return 0;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Contact record) throws Exception {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Contact record) throws Exception {
        return 0;
    }
}
