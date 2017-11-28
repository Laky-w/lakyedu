package com.laky.edu.organization.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

/**
 * Created by 95 on 2017/11/20.
 */
public class SchoolZone {
    private Integer id;
    private String name;
    private Integer theStatus;//状态 1 校区 0 删除 2 部门
    private Integer theType;
    private Date createDatetime;
    private String serial;
    private String address;
    private String phone;
    private Integer fatherId;
    private Branch branch;
    private String remarks;
    private String owner;
    private List<SchoolZone> childrenList;

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
        this.name = name;
    }

    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }

    public Integer getTheType() {
        return theType;
    }

    public void setTheType(Integer theType) {
        this.theType = theType;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    @JsonIgnore()
    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<SchoolZone> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<SchoolZone> childrenList) {
        this.childrenList = childrenList;
    }
}
