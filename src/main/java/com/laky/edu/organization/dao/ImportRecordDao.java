package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.ImportRecord;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface ImportRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ImportRecord record);

    int insertSelective(ImportRecord record);

    ImportRecord selectByPrimaryKey(Integer id);

    List<ImportRecord> selectByParameterMap(LinkedHashMap parametermap);

    int updateByPrimaryKeySelective(ImportRecord record);

    int updateByPrimaryKey(ImportRecord record);
}