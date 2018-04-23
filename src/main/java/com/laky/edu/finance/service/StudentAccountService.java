package com.laky.edu.finance.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.StudentAccount;

import java.util.LinkedHashMap;

public interface StudentAccountService {
    /**
     * 查询学生账户
     * @param parameterMap
     * @return
     */
    PageBean<StudentAccount> findStudentAccountAllBySchool(LinkedHashMap parameterMap) throws Exception;
}
