package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.dao.SchoolZoneDao;
import com.laky.edu.organization.service.SchoolZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public Map findSchoolZone(LinkedHashMap parameterMap) throws Exception {
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
            return null;
    }

    @Transactional
    @Override
    public String deleteSchoolZone(Integer schoolId,Integer branchId) throws Exception {
        SchoolZone schoolZone = querySchoolZoneAllBySchoolZoneId(branchId,schoolId,0);
        List<SchoolZone> childrenList = schoolZone.getChildrenList();
        List<SchoolZone> dataList = new ArrayList<>();
        dataList.add(schoolZone);
        if(childrenList!=null && childrenList.size()>0){
            List tempList=getSubList(schoolZone.getId(),schoolZone.getChildrenList(),0);
            if(tempList !=null)dataList.addAll(tempList);
        }
        String log="删除校区【"+schoolZone.getName()+"】,下级校区数量："+(dataList.size()-1);
        schoolZoneDao.batchStatusSchoolZone(dataList,0);
        return log;
    }

    @Override
    public String sealUpSchoolZone(Integer schoolId, Integer branchId) throws Exception {
        SchoolZone schoolZone = querySchoolZoneAllBySchoolZoneId(branchId,schoolId,0);
        List<SchoolZone> childrenList = schoolZone.getChildrenList();
        List<SchoolZone> dataList = new ArrayList<>();
        dataList.add(schoolZone);
        if(childrenList!=null && childrenList.size()>0){
            List tempList=getSubList(schoolZone.getId(),schoolZone.getChildrenList(),0);
            if(tempList !=null)dataList.addAll(tempList);
        }
        String log="封存校区【"+schoolZone.getName()+"】,下级校区数量："+(dataList.size()-1);
        schoolZoneDao.batchStatusSchoolZone(dataList,2);
        return log;
    }

    @Override
    public String normalSchoolZone(Integer schoolId, Integer branchId) throws Exception {
        SchoolZone schoolZone = querySchoolZoneAllBySchoolZoneId(branchId,schoolId,0);
        List<SchoolZone> dataList = new ArrayList<>();
        dataList.add(schoolZone);
        String log="启用校区【"+schoolZone.getName()+"】";
        schoolZoneDao.batchStatusSchoolZone(dataList,1);
        return log;
    }

    /**
     * 查找子校区
     * @param id
     * @param schoolZoneList
     * @return
     */
    private List<SchoolZone> getSubList(Integer id,List<SchoolZone> schoolZoneList,int theType){
        List<SchoolZone> newSchoolZoneList = new ArrayList<>();
        schoolZoneList.forEach(item->{
            if(item.getFatherId() == id ){
                if((theType ==0) || (item.getTheType() == theType)) {
                    newSchoolZoneList.add(item);
                    List tempList=getSubList(item.getId(),schoolZoneList,theType);
                    if(tempList !=null)newSchoolZoneList.addAll(tempList);
                }
            }
        });
        if (newSchoolZoneList.size() == 0) return  null;
        return newSchoolZoneList;
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
