package com.laky.edu.organization.bean;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    private Integer id;

    private Integer userId;

    private User user;

    private Date createDatetime;

    private Integer theType;

    private Integer theStatus;

    private Date lastDatetime;

    private Integer branchId;

    private Integer schoolZoneId;

    private SchoolZone schoolZone;

    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SchoolZone getSchoolZone() {
        return schoolZone;
    }

    public void setSchoolZone(SchoolZone schoolZone) {
        this.schoolZone = schoolZone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Integer getTheType() {
        return theType;
    }

    public void setTheType(Integer theType) {
        this.theType = theType;
    }

    public Date getLastDatetime() {
        return lastDatetime;
    }

    public void setLastDatetime(Date lastDatetime) {
        this.lastDatetime = lastDatetime;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolId) {
        this.schoolZoneId = schoolId;
    }

    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }

}