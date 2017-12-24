package com.laky.edu.reception.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.reception.bean.Student;
import com.laky.edu.reception.dao.StudentDao;
import com.laky.edu.reception.service.StudentService;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.dao.CustomerDao;
import com.laky.edu.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by 湖之教育工作室·laky on 2017/12/23.
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CustomerDao customerDao;

    @Transactional
    @Override
    public Student createStudent(Student student,Customer customer) throws Exception {
        //创建生源记录
        customer.setTheStatus(1);
        customer.setTheType(1);//生源类型处理
        customer.setCreateTime(new Date());
        //PinyinHelper.
        customer.setPinyin(PinYinUtil.parsePinYin(customer.getName()));
        customerDao.insertCustomer(customer);
        //创建正式学员记录 报班状态（未报班）
        student.setTheStatus(1);//正常
        student.setTheType(1);
        student.setCreateTime(customer.getCreateTime());
        student.setPinyin(customer.getPinyin());
        student.setCustomerId(customer.getId());
//        student.
        student.setClassStatus(1);//1未报班 2已报班，其他状态待定
        int rowIndex = studentDao.insert(student);
        if(rowIndex==0) throw new Exception("创建学员失败！");
        return student;
    }

    @Override
    public PageBean<Student> findStudentAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(studentDao.selectByParameterMap(parameterMap));
    }
}
