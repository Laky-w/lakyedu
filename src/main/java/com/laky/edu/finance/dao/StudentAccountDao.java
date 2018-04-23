package com.laky.edu.finance.dao;

import com.laky.edu.finance.bean.StudentAccount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentAccountDao {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentAccount record);

    int batchInsert(List<StudentAccount> recordList);

    int insertSelective(StudentAccount record);

    StudentAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentAccount record);

    int updateByPrimaryKey(StudentAccount record);
}