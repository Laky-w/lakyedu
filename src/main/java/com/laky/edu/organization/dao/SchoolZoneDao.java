package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.SchoolZone;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95 on 2017/11/20.
 */
@Component
public interface SchoolZoneDao {
    /**
     * 插入一个校区
     * @param schoolZone
     * @return
     */
    int insertSchoolZoneDao(SchoolZone schoolZone);

    /**
     * 查询机构下的全部校区
     * @param branchId
     * @return
     */
    List<SchoolZone> querySchoolZoneAllByBranchId(Integer branchId);

    /**
     * 查询某个校区根据ID
     * @param parameterMap
     * @return
     */
    Map querySchoolZone(LinkedHashMap parameterMap);

    /**
     * 修改校区信息
     * @param schoolZone
     * @return
     */
    int updateSchoolZone(SchoolZone schoolZone);

    /**
     * 批量删除校区
     * @param schoolZoneList
     * @return
     */
    int batchStatusSchoolZone(@Param(value = "list") List<SchoolZone> schoolZoneList,@Param(value = "status") Integer status);



}
