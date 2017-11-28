package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.dao.SchoolZoneDao;
import com.laky.edu.organization.service.SchoolZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 95 on 2017/11/20.
 */
@Service
public class SchoolZoneServiceImpl implements SchoolZoneService{
    @Autowired
    private SchoolZoneDao schoolZoneDao;

    @Transactional
    @Override
    public int insertSchoolZoneDao(SchoolZone schoolZone) throws Exception {
        //SchoolZone schoolZone = new SchoolZone();
        /*
        默认值处理
         */
        schoolZone.setTheStatus(1);
        if(schoolZone.getTheType() == null) {
            schoolZone.setTheType(1);
        }
        schoolZone.setCreateDatetime(new Date());
        return schoolZoneDao.insertSchoolZoneDao(schoolZone);
    }

    @Override
    public List<SchoolZone> querySchoolZoneAllByBranchId(Integer branchId) throws Exception {
        return schoolZoneDao.querySchoolZoneAllByBranchId(branchId);
    }

    @Override
    public SchoolZone querySchoolZoneAllBySchoolZoneId(Integer branchId, Integer schoolZoneId) throws Exception {
        List<SchoolZone> list = schoolZoneDao.querySchoolZoneAllByBranchId(branchId);
        List<SchoolZone> newSchoolZoneList = new ArrayList<>();
        list.forEach(schoolZone -> {
            if(schoolZone.getId() == schoolZoneId) {
                schoolZone.setChildrenList(getSubs(schoolZone.getId(),list));
                newSchoolZoneList.add(schoolZone);
                return;
            }
        });
        if(newSchoolZoneList.size()>0)
            return newSchoolZoneList.get(0);
        else
            return new SchoolZone();
    }


    /**
     * 查找子校区
     * @param id
     * @param schoolZoneList
     * @return
     */
    private List<SchoolZone> getSubs(Integer id,List<SchoolZone> schoolZoneList){
        List<SchoolZone> newSchoolZoneList = new ArrayList<>();
        schoolZoneList.forEach(item->{
            if(item.getFatherId() == id){
                item.setChildrenList(getSubs(item.getId(),schoolZoneList));
                newSchoolZoneList.add(item);
            }
        });
        if (newSchoolZoneList.size() == 0) return  null;
        return newSchoolZoneList;
    }

    @Override
    public SchoolZone querySchoolZoneDaoById(Integer id) throws Exception {
        return schoolZoneDao.querySchoolZoneDaoById(id);
    }

    @Override
    public int updateSchoolZone(SchoolZone schoolZone) throws Exception {
        return schoolZoneDao.updateSchoolZone(schoolZone);
    }
}
