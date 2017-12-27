package com.laky.edu.reception.dao;

import com.laky.edu.reception.bean.StudentOrderDetail;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface StudentOrderDetailDao {

    int insert(StudentOrderDetail studentOrderDetail);

    int batchInsert(List<StudentOrderDetail> studentOrderDetail);

    List<StudentOrderDetail> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(StudentOrderDetail record);

    int updateByPrimaryKey(StudentOrderDetail record);
}