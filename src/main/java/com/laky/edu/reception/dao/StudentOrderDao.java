package com.laky.edu.reception.dao;

import com.laky.edu.reception.bean.StudentOrder;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface StudentOrderDao {

    int insert(StudentOrder studentOrder);

    StudentOrder selectById(Integer id);

    List<StudentOrder> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(StudentOrder record);

    int updateByPrimaryKey(StudentOrder record);
}