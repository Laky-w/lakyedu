package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.SchoolClass;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface SchoolClassDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolClass schoolClass);

    int insertSelective(SchoolClass record);

    Map selectSchoolClassById(LinkedHashMap parameterMap);

    List<Map> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(SchoolClass record);

    int updateByPrimaryKey(SchoolClass record);
}