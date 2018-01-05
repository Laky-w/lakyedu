package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.dao.SchoolZoneDao;
import com.laky.edu.organization.service.SchoolZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
    public SchoolZone findSchoolZone(LinkedHashMap parameterMap) throws Exception {
        return schoolZoneDao.querySchoolZone(parameterMap);
    }

    @Override
    public List<SchoolZone> querySchoolZoneAllByBranchId(Integer branchId) throws Exception {
        return schoolZoneDao.querySchoolZoneAllByBranchId(branchId);
    }

    @Override
    public SchoolZone querySchoolZoneAllBySchoolZoneId(Integer branchId, Integer schoolZoneId,Integer theType) throws Exception {
        List<SchoolZone> list = schoolZoneDao.querySchoolZoneAllByBranchId(branchId);
        List<SchoolZone> newSchoolZoneList = new ArrayList<>();
        list.forEach(schoolZone -> {
            if(schoolZone.getId() == schoolZoneId ) {
                schoolZone.setChildrenList(getSubs(schoolZone.getId(),list,theType));
                newSchoolZoneList.add(schoolZone);
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
    private List<SchoolZone> getSubs(Integer id,List<SchoolZone> schoolZoneList,int theType){
        List<SchoolZone> newSchoolZoneList = new ArrayList<>();
        schoolZoneList.forEach(item->{
            if(item.getFatherId() == id ){
                if((theType ==0) || (item.getTheType() == theType)) {
                    item.setChildrenList(getSubs(item.getId(),schoolZoneList,theType));
                    newSchoolZoneList.add(item);
                }
            }
        });
        if (newSchoolZoneList.size() == 0) return  null;
        newSchoolZoneList.sort((a,b) -> b.getTheType().compareTo(a.getTheType()));
        return newSchoolZoneList;
    }



    @Override
    public int updateSchoolZone(SchoolZone schoolZone) throws Exception {
        return schoolZoneDao.updateSchoolZone(schoolZone);
    }
}
