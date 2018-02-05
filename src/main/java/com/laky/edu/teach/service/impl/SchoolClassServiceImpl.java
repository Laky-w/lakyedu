package com.laky.edu.teach.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.reception.ReceptionConst;
import com.laky.edu.reception.bean.StudentClass;
import com.laky.edu.reception.dao.StudentClassDao;
import com.laky.edu.teach.bean.SchoolClass;
import com.laky.edu.teach.dao.SchoolClassDao;
import com.laky.edu.teach.service.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2017/12/11.
 */
@Service
public class SchoolClassServiceImpl implements SchoolClassService{
    @Autowired
    private SchoolClassDao schoolClassDao;
    @Autowired
    private StudentClassDao studentClassDao;
    @Override
    public PageBean<Map> findSchoolClassAllBySchool(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(schoolClassDao.selectByParameterMap(parameterMap));
    }
    @Transactional
    @Override
    public SchoolClass createSchoolClass(SchoolClass schoolClass) throws Exception {
        schoolClass.setTheStatus(1);
        schoolClass.setCreateTime(new Date());
        int rows=schoolClassDao.insert(schoolClass);
        if(rows==0) throw new Exception("创建班级失败！");
        return schoolClass;
    }

    @Override
    public Map findSchoolClassById(LinkedHashMap parameterMap) throws Exception {
        return schoolClassDao.selectSchoolClassById(parameterMap);
    }

    @Override
    public PageBean<Map> findWaitStudentAll(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(studentClassDao.selectByParameterMap(parameterMap));
    }
    @Transactional
    @Override
    public int deleteSchoolZone(Integer id) throws Exception {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(id);
        schoolClass.setTheStatus(0);
        int rows = schoolClassDao.updateSchoolClass(schoolClass);
        if (rows==0) throw new RuntimeException("删除班级失败!");
        return rows;
    }
    @Transactional
    @Override
    public SchoolClass updateSchoolZOne(SchoolClass schoolClass) throws Exception {
        int rows = schoolClassDao.updateSchoolClass(schoolClass);
        if (rows==0)throw new RuntimeException("更新班级失败!");
        return schoolClass;
    }

    @Transactional
    @Override
    public int doUpdateStudentClass(LinkedHashMap parameterMap) throws Exception {
//        LinkedHashMap parameterMap = new LinkedHashMap();
//        parameterMap.put("ids",ids);
//        parameterMap.put("sch")
        Integer classId =(Integer)parameterMap.get("classId");
        parameterMap.remove("classId");
        List<Map>  studentClassList =studentClassDao.selectByParameterMap(parameterMap);
        if(studentClassList.size()==0) {
            throw  new RuntimeException("分班异常");
        }
        List<StudentClass> studentClasses = new ArrayList<>();
        for (Map map :studentClassList){
            StudentClass studentClass = new StudentClass();
            studentClass.setId((Integer) map.get("id"));
            studentClass.setClassId(classId);
            studentClass.setClassStatus(ReceptionConst.STUDENT_CLASS_STATUS_STUDYING);
            studentClass.setCreateTime(new Date());
            studentClasses.add(studentClass);
        }

        return studentClassDao.batchUpdateByPrimaryKeySelective(studentClasses);
    }
}
