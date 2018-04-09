package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.ImportDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ImportDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ImportDetail record);

    int batchInsert(List<ImportDetail> record);

    int insertSelective(ImportDetail record);

    ImportDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImportDetail record);

    int updateByPrimaryKeyWithBLOBs(ImportDetail record);

    int updateByPrimaryKey(ImportDetail record);
}