package com.laky.edu.organization.service;

import com.laky.edu.organization.bean.SchoolZone;

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
     * 查询某个校区根据ID
     * @param id
     * @return
     */
    SchoolZone querySchoolZoneDaoById(Integer id) throws Exception;

    /**
     * 修改校区信息
     * @param schoolZone
     * @return
     */
    int updateSchoolZone(SchoolZone schoolZone) throws Exception;



}
