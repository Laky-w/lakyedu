package com.laky.edu.reception.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.reception.bean.Student;
import com.laky.edu.supply.bean.Customer;

import java.util.LinkedHashMap;

/**
 * Created by 湖之教育工作室·laky on 2017/12/23.
 */
public interface StudentService {
    /**
     * 创建一个正式学生
     * @param student
     * @return
     */
    Student createStudent(Student student,Customer customer) throws Exception;

    /**
     * 查询正式学员
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<Student> findStudentAll(LinkedHashMap parameterMap)throws Exception;
}
