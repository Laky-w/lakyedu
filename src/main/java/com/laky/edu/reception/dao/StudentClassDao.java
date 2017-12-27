package com.laky.edu.reception.dao;

import com.laky.edu.reception.bean.StudentClass;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface StudentClassDao {

    int insert(StudentClass studentClass);

    int batchInsert(List<StudentClass> studentClass);

    List<StudentClass> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(StudentClass record);

    int updateByPrimaryKey(StudentClass record);
}