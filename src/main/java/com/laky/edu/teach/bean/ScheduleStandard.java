package com.laky.edu.teach.bean;

import java.io.Serializable;

public class ScheduleStandard implements Serializable {
    private Integer id;

    private Integer schoolZoneId;

    private String startTime;

    private String endTime;

    private Double courseHour;

    private String name;

    private Integer theStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Double getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(Double courseHour) {
        this.courseHour = courseHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }
}