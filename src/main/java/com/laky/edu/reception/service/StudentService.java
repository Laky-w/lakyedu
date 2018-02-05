package com.laky.edu.reception.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.MoneyRecordAccount;
import com.laky.edu.reception.bean.Student;
import com.laky.edu.reception.bean.StudentOrder;
import com.laky.edu.reception.bean.StudentOrderDetail;
import com.laky.edu.supply.bean.Customer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 学生报名
     * @param studentId
     * @param order
     * @param orderDetails
     * @param financeAccount
     * @param userId
     * @return
     * @throws Exception
     */
    StudentOrder studentApply(Integer studentId, StudentOrder order, List<StudentOrderDetail> orderDetails,
                      List<MoneyRecordAccount> financeAccount,Integer userId) throws Exception;

    /**
     * 查询正式学员记录
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Map queryStudent(LinkedHashMap parameterMap)throws Exception;

    /**
     * 删除正式学员
     * @param studentMap
     * @return
     * @throws Exception
     */
    boolean deleteStudent(Map studentMap)throws Exception;

    /**
     * 更新正式学员
     * @param student
     * @return
     * @throws Exception
     */
    Student updateStudent(Student student,Customer customer)throws Exception;

    /**
     * 分配学管师
     * @param ownerId
     * @param students
     * @return
     * @throws Exception
     */
    int updateStudentManage(Integer ownerId,Integer [] students) throws Exception;
}
