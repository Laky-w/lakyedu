package com.laky.edu.teach.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.SchoolClass;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/11.
 */
public interface SchoolClassService {
    /**
     * 查询班级
     * @param parameterMap
     * @return
     */
    PageBean<Map> findSchoolClassAllBySchool(LinkedHashMap parameterMap);

    /**
     *  增加班级
     * @param schoolClass
     * @return
     * @throws Exception
     */
    SchoolClass createSchoolClass(SchoolClass schoolClass) throws Exception;

    /**
     * 查询待分班学员
     * @param parameterMap
     * @return
     */
    PageBean<Map> findWaitStudentAll(LinkedHashMap parameterMap);
}
