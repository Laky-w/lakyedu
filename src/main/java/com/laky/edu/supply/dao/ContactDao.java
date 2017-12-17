package com.laky.edu.supply.dao;

import com.laky.edu.supply.bean.Contact;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface ContactDao {
    int insert(Contact contact);

    List<Contact> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Contact record);

    int updateByPrimaryKeyWithBLOBs(Contact record);

    int updateByPrimaryKey(Contact record);
}