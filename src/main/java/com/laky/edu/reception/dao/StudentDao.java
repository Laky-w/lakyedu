package com.laky.edu.reception.dao;

import com.laky.edu.reception.bean.Student;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface StudentDao {

    int insert(Student record);

    int insertSelective(Student record);

    /**
     * 学员详情页dao
     * @param parameterMap
     * @return
     */
    Map selectStudent(LinkedHashMap parameterMap);

    Student selectByPrimaryKey(Integer id);

    List<Student> selectByParameterMap(LinkedHashMap parameterMap);

    /**
     *修改正式学员
     * @param student
     * @return
     */
    int updateStudent(Student student);

    int updateByPrimaryKey(Student record);

    int batchUpdateByPrimaryKey(List<Student> studentList);
}