package com.laky.edu.teach.web.form;

import com.laky.edu.teach.bean.Schedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2018/1/2.
 */
public class ScheduleForm {
    private Integer scheduleId;//排课记录id
    private Integer classId;
    private String className;
    private Integer teacherId;
    private String teacherName;
    private Integer roomId;
    private String  roomName;
    private Integer[] helpTeacherId;
    private Date[] scheduleDate;
    private Integer maxCount;
    private Integer schoolZoneId;
    private List<Map> classTimes;
    private Boolean isChecked;

    public List<Map> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<Map> classTimes) {
        this.classTimes = classTimes;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer[] getHelpTeacherId() {
        return helpTeacherId;
    }

    public void setHelpTeacherId(Integer[] helpTeacherId) {
        this.helpTeacherId = helpTeacherId;
    }

    public Date[] getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date[] scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }
}
