package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.BranchParameterValue;

import java.util.LinkedHashMap;
import java.util.List;

public interface BranchParameterValueDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BranchParameterValue record);

    int insertSelective(BranchParameterValue record);

    BranchParameterValue queryBranchParameterValueByPrimaryKey(Integer id);

    /**
     * 查询用户参数通过机构id
     * @param parameterMap
     * @return
     */
    List<BranchParameterValue> queryBranchParameterValueByBranchId(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(BranchParameterValue record);

    int updateByPrimaryKey(BranchParameterValue record);
}