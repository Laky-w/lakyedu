package com.laky.edu.log.bean;

import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.bean.User;

import java.io.Serializable;
import java.util.Date;

public class OperateLog implements Serializable {
    private Integer id;

    private Date createTime;

    private String operatePerson;

    private String title;

    private Integer theType;

    private Integer branchId;

    private Integer accountId;

    private User user;

    private Integer schoolZoneId;

    private SchoolZone schoolZone;

    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperatePerson() {
        return operatePerson;
    }

    public void setOperatePerson(String operatePerson) {
        this.operatePerson = operatePerson == null ? null : operatePerson.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getTheType() {
        return theType;
    }

    public void setTheType(Integer theType) {
        this.theType = theType;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }


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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", operatePerson=").append(operatePerson);
        sb.append(", title=").append(title);
        sb.append(", theType=").append(theType);
        sb.append(", branchId=").append(branchId);
        sb.append(", accountId=").append(accountId);
        sb.append(", schoolZoneId=").append(schoolZoneId);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}