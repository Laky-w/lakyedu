package com.laky.edu.finance.service.impl;

import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.StudentAccount;
import com.laky.edu.finance.service.StudentAccountService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class StudentAccountServiceImpl implements StudentAccountService {
    @Override
    public PageBean<StudentAccount> findStudentAccountAllBySchool(LinkedHashMap parameterMap) throws Exception {
        return null;
    }
}
