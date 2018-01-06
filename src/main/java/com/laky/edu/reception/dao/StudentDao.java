package com.laky.edu.reception.dao;

import com.laky.edu.reception.bean.Student;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface StudentDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Map selectStudent(LinkedHashMap parameterMap);

    Student selectByPrimaryKey(Integer id);

    List<Student> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}