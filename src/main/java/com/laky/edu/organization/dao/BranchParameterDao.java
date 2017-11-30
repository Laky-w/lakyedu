package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.BranchParameter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface BranchParameterDao {
    int insertUserParameter(BranchParameter userParameter);

    List<BranchParameter> queryUserParameterById(Integer Id);

    List<BranchParameter> queryBranchParameterAll();
}