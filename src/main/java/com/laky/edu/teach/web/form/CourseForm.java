package com.laky.edu.teach.web.form;

import com.alibaba.fastjson.JSONArray;
import com.laky.edu.teach.bean.Course;

/**
 * Created by 湖之教育工作室·laky on 2018/1/19.
 */
public class CourseForm {
    private Course course;
    private String [] schoolIds;
    private JSONArray chargeStandard;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String[] getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(String[] schoolIds) {
        this.schoolIds = schoolIds;
    }

    public JSONArray getChargeStandard() {
        return chargeStandard;
    }

    public void setChargeStandard(JSONArray chargeStandard) {
        this.chargeStandard = chargeStandard;
    }
}
