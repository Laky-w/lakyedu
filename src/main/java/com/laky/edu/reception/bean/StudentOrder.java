package com.laky.edu.reception.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StudentOrder implements Serializable {
    private Integer id;

    private Integer studentId;

    private Integer theStatus;

    private Integer userId;

    private Date createTime;

    private Integer schoolZoneId;

    private BigDecimal total;

    private BigDecimal receivable;

    private BigDecimal subtractMoney;

    private Integer theType;

    private Integer costStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getReceivable() {
        return receivable;
    }

    public void setReceivable(BigDecimal receivable) {
        this.receivable = receivable;
    }

    public BigDecimal getSubtractMoney() {
        return subtractMoney;
    }

    public void setSubtractMoney(BigDecimal subtractMoney) {
        this.subtractMoney = subtractMoney;
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

    public Integer getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(Integer costStatus) {
        this.costStatus = costStatus;
    }
}