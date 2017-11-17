package com.laky.edu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laky.edu.bean.Branch;
import com.laky.edu.core.PageBean;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 95 on 2017/11/14.
 */
public interface OrganizationService {


    /**
     * 查找机构，通过机构代码
     * @param serial
     * @return
     */
    Branch findBranchBySerial(String serial);

    /**
     * 查找机构，通过机构代码
     * @param serial
     * @param id
     * @return
     */
    Branch findBranchBySerialOrId(String serial,Integer id);

    /**
     * 创建一个机构
     * @param branch
     * @return
     */
    int createNewBranch(Branch branch);

    /**
     * 更新机构信息
     * @param
     * @return
     */
    int updateBranchById(Branch branch) throws Exception;

    /**
     * 查询全部机构
     * @param pageNum 页数
     * @param pageSize 条数
     * @return
     * @throws Exception
     */
    PageBean findBranchAll(int pageNum, int pageSize) throws Exception;

    /**
     * 查询
     * @return
     */
    List<Branch> findBranchAll(LinkedHashMap parameterMap) throws Exception;
}
