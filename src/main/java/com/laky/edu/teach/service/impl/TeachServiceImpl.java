package com.laky.edu.teach.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import com.laky.edu.teach.dao.CourseDao;
import com.laky.edu.teach.dao.RoomDao;
import com.laky.edu.teach.service.TeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2017/12/10.
 */
@Service
public class TeachServiceImpl implements TeachService{
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private RoomDao roomDao;

    @Override
    public PageBean<Course> findCourseByBranch(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(courseDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public Course createCourse(Course course,String[] schoolIds, JSONArray chargeStandards) throws Exception {
        course.setTheStatus(1);
        int rows=courseDao.insert(course);
        if(rows==0) throw new Exception("创建课程失败！");
        //课程收费
        if(chargeStandards!=null && chargeStandards.size()>0){
            List<Map> chargeStandardList = new ArrayList<>();
            for(Object object:chargeStandards){
                Map objectMap = (Map) object;
                objectMap.put("courseId",course.getId());
                objectMap.put("theType",1);
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
        return course;
    }

    @Override
    public PageBean<Room> findRoomAll(LinkedHashMap parameterMap) {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(roomDao.selectByParameterMap(parameterMap));
    }

    @Override
    public Room createRoom(Room room) throws Exception {
        room.setTheStatus(1);
        int rows=roomDao.insert(room);
        if(rows==0) throw new Exception("创建教室失败！");
        return room;
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
}
