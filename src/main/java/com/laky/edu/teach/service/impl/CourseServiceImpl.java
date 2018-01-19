package com.laky.edu.teach.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.dao.CourseDao;
import com.laky.edu.teach.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2018/1/19.
 */
@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseDao courseDao;
    @Override
    public PageBean<Course> findCourseBySchoolZone(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(courseDao.selectByParameterMap(parameterMap));
    }

    @Override
    public PageBean<Course> findCourseByBranch(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(courseDao.selectBranchByParameterMap(parameterMap));
    }

    @Override
    public List<Map> findChargeStandardByCourseId(LinkedHashMap parameterMap) {
        return courseDao.selectCourseChargeStandard(parameterMap);
    }

    @Transactional
    @Override
    public Course createCourse(Course course,String[] schoolIds, JSONArray chargeStandards) throws Exception {
        course.setTheStatus(1);
        int rows=courseDao.insert(course);
        if(rows==0) throw new RuntimeException("创建课程失败！");
        doSaveCourseOther(schoolIds,chargeStandards,course);
        return course;
    }

    @Transactional
    @Override
    public Course updateCourse(Course course, String[] schoolIds, JSONArray chargeStandards) throws Exception {
        int rows= courseDao.updateByPrimaryKeySelective(course);
        if(rows==0) throw new RuntimeException("修改课程失败！");
        courseDao.deleteCourseSchool(course.getId());
        courseDao.deleteChargeStandard(course.getId());
        doSaveCourseOther(schoolIds,chargeStandards,course);
        return course;
    }


    private void doSaveCourseOther(String[] schoolIds, JSONArray chargeStandards,Course course) throws Exception{
        //课程收费
        if(chargeStandards!=null && chargeStandards.size()>0){
            List<Map> chargeStandardList = new ArrayList<>();
            for(Object object:chargeStandards){
                Map objectMap = (Map) object;
                objectMap.put("courseId",course.getId());
                if(course.getStandardType().intValue()==1){
                    objectMap.put("maxHourse",0);
                }
                objectMap.put("theType",course.getStandardType());
                chargeStandardList.add(objectMap);
            }
            courseDao.insertCourseChargeStandard(chargeStandardList);
        }
        //课程授权
        if (schoolIds!=null && schoolIds.length>0) {
            List<Map> courseSchoolList = new ArrayList<>();
            for(int i=0;i<schoolIds.length;i++){
                Map objectMap = new HashMap<>();
                objectMap.put("schoolZoneId",schoolIds[i]);
                objectMap.put("courseId",course.getId());
                courseSchoolList.add(objectMap);
            }
            courseDao.insertCourseSchool(courseSchoolList);
        }
    }

    @Transactional
    @Override
    public String  deleteCourse(Integer courseId, Integer branchId) {
        Course course = courseDao.selectCourseById(courseId);
        course.setTheStatus(0);
        courseDao.updateByPrimaryKeySelective(course);
        String log = "删除课程【"+course.getName()+"】";
        return log;
    }

    @Override
    public String sealUpOrNormalCourse(Integer courseId, Integer branchId,Integer theStatus) throws Exception {
        Course course = courseDao.selectCourseById(courseId);
        course.setTheStatus(theStatus);
        courseDao.updateByPrimaryKeySelective(course);
        String log = theStatus.intValue() ==1?"启用课程【"+course.getName()+"】":"封存课程【"+course.getName()+"】";
        return log;
    }

    @Transactional
    @Override
    public Integer[] updateCourseSchool(Integer courseId, Integer[] schoolIds) throws Exception {
        List<Map> courseSchoolList = new ArrayList<>();
        courseDao.deleteCourseSchool(courseId);
        for(int i=0;i<schoolIds.length;i++){
            Map objectMap = new HashMap<>();
            objectMap.put("schoolZoneId",schoolIds[i]);
            objectMap.put("courseId",courseId);
            courseSchoolList.add(objectMap);
        }
        courseDao.insertCourseSchool(courseSchoolList);
        return schoolIds;
    }

    @Override
    public Map queryCourse(LinkedHashMap parameterMap) throws Exception {
        return courseDao.selectCourse(parameterMap);
    }
    @Override
    public List<Map> findCourseTreeByBranch(LinkedHashMap parameterMap) {

        //第一步 获取数据
        List<Course> dataList =courseDao.selectByParameterMap(parameterMap);
        List<Map> returnList = new ArrayList<>();
        Map<Integer,Object> map = new HashMap<>();
        dataList.forEach(course -> { //获取科目
            map.put(course.getClazzId(),course.getClazzName());
        });
        String [] courseType = {"一对一","一对多"};
        //数据处理，科目，类型，课程 层级处理
        for (Integer key : map.keySet()) {
            Map dataMap = new HashMap<>();
            dataMap.put("value",key);
            dataMap.put("label",map.get(key));
            List<Map> childrenList = new ArrayList<>();
            Map typeMap = new HashMap<>(); //保证类型唯一性
            dataList.forEach(course -> {
                if(course.getClazzId()==key){ //这个科目下的子级
                    if(typeMap.get(course.getTheType())==null){
                        //新建类型
                        Map typeDataMap = new HashMap<>();
                        typeDataMap.put("value",course.getTheType());
                        typeDataMap.put("label",courseType[course.getTheType()-1]);
                        List<Map> typeChildrenList = new ArrayList<>();
                        typeDataMap.put("children", typeChildrenList);
                        childrenList.add(typeDataMap);//加到父亲上去
                        typeMap.put(course.getTheType(),typeDataMap);
                    }
                    Map courseMap = new HashMap<>();
                    courseMap.put("value",course.getId());
                    courseMap.put("label",course.getName());
                    Map newMap =(Map) typeMap.get(course.getTheType());
                    List<Map> typeChildrenListTemp=(List<Map>) newMap.get("children");
                    typeChildrenListTemp.add(courseMap);
                }
            });
            if(childrenList.size()>0) dataMap.put("children",childrenList);
            returnList.add(dataMap);
        }
        return returnList;
    }

    @Override
    public List<Map> findCourseSchool(LinkedHashMap parameterMap) {
        return courseDao.selectCourseSchool(parameterMap);
    }
}
