package com.laky.edu.teach.web.form;

import com.laky.edu.teach.bean.Attendance;

import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2018/3/14.
 */
public class AttendanceForm {
    private Integer scheduleId;
    private List<Attendance> attendanceList;

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
