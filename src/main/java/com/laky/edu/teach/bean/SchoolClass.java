package com.laky.edu.teach.bean;

import java.io.Serializable;
import java.util.Date;

public class SchoolClass implements Serializable {
    private Integer id;

    private String name;

    private Integer courseId;

    private Integer schoolZoneId;

    private Integer theStatus;

    private Integer rooId;

    private Integer mainTeacherId;

    private Integer teacherId;

    private Date startDate;

    private Date endDate;

    private String remarks;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }

    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }

    public Integer getRooId() {
        return rooId;
    }

    public void setRooId(Integer rooId) {
        this.rooId = rooId;
    }

    public Integer getMainTeacherId() {
        return mainTeacherId;
    }

    public void setMainTeacherId(Integer mainTeacherId) {
        this.mainTeacherId = mainTeacherId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

}