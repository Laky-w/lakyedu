package com.laky.edu.organization.service;

import com.laky.edu.organization.bean.SchoolZone;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 95 on 2017/11/20.
 */
public interface SchoolZoneService {
    /**
     * 插入一个校区
     * @param schoolZone
     * @return
     */
    int insertSchoolZoneDao(SchoolZone schoolZone) throws Exception;

    /**
     * 查询机构下的全部校区
     * @param branchId
     * @return
     */
    List<SchoolZone> querySchoolZoneAllByBranchId(Integer branchId) throws Exception;

    /**
     * 查询校区详情
     * @param parameterMap
     * @return
     * @throws Exception
     */
    SchoolZone findSchoolZone(LinkedHashMap parameterMap) throws Exception;

    /**
     * 查询校区下的全部校区
     * @param branchId
     * @param schoolZoneId
     * @param theType 0 全部子校区和部门 2 子校区 3子部门
     * @return
     * @throws Exception
     */
    SchoolZone querySchoolZoneAllBySchoolZoneId(Integer branchId,Integer schoolZoneId,Integer theType) throws Exception;



    /**
     * 修改校区信息
     * @param schoolZone
     * @return
     */
    int updateSchoolZone(SchoolZone schoolZone) throws Exception;



}
