package com.laky.edu.dao;

import com.laky.edu.bean.Branch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 95 on 2017/11/14.
 */
@Component
public interface BranchDao {
    /**
     * 查询全部
     * @return
     */
    List<Branch> findBranchAll();


    /**
     * 插入一个机构
     * @param branch
     * @return
     */
    int insertBranch(Branch branch);

    /**
     * 查询机构实体，通过机构代码查询条件
     * @return
     */
    Branch queryBranchBySerial(@Param(value = "serial") String serial);

    /**
     * 查询机构实体，通过机构代码或者ID
     * @param parameterMap
     * @return
     */
    Branch queryBranchBySerialOrId(LinkedHashMap parameterMap);

    /**
     * 更新机构
     * @param
     * @return
     */
    int updateBranchById( Branch branch);
}
