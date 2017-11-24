package com.laky.edu.log.bean;

import java.io.Serializable;
import java.util.Date;

public class LoginLog implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer schoolZoneId;

    private Integer branchId;

    private String ip;

    private Integer theType;

    private Date theDatetime;

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

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getTheType() {
        return theType;
    }

    public void setTheType(Integer theType) {
        this.theType = theType;
    }

    public Date getTheDatetime() {
        return theDatetime;
    }

    public void setTheDatetime(Date theDatetime) {
        this.theDatetime = theDatetime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", schoolZoneId=").append(schoolZoneId);
        sb.append(", branchId=").append(branchId);
        sb.append(", ip=").append(ip);
        sb.append(", theType=").append(theType);
        sb.append(", theDatetime=").append(theDatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}