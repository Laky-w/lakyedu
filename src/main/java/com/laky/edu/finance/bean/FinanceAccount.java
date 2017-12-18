package com.laky.edu.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FinanceAccount implements Serializable {
    private Integer id;

    private String name;

    private Integer theStatus;

    private Integer schoolZoneId;

    private Integer branchId;

    private BigDecimal money;

    private Integer theOpen;

    private Integer theType;

    private Integer thePublic;

    private Date theTime;

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
        this.name = name;
    }

    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getTheOpen() {
        return theOpen;
    }

    public void setTheOpen(Integer theOpen) {
        this.theOpen = theOpen;
    }

    public Integer getTheType() {
        return theType;
    }

    public void setTheType(Integer theType) {
        this.theType = theType;
    }

    public Integer getThePublic() {
        return thePublic;
    }

    public void setThePublic(Integer thePublic) {
        this.thePublic = thePublic;
    }

    public Date getTheTime() {
        return theTime;
    }

    public void setTheTime(Date theTime) {
        this.theTime = theTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}