package com.laky.edu.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StudentAccount implements Serializable {
    private Integer id;

    private Integer studentId;

    private Integer courseId;

    private Integer orderId;

    private BigDecimal totalMoney;

    private BigDecimal totalHour;

    private BigDecimal lastMoney;

    private BigDecimal lastHour;

    private BigDecimal price;

    private Date theTime;

    private Integer theStatus;

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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(BigDecimal totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getLastMoney() {
        return lastMoney;
    }

    public void setLastMoney(BigDecimal lastMoney) {
        this.lastMoney = lastMoney;
    }

    public BigDecimal getLastHour() {
        return lastHour;
    }

    public void setLastHour(BigDecimal lastHour) {
        this.lastHour = lastHour;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getTheTime() {
        return theTime;
    }

    public void setTheTime(Date theTime) {
        this.theTime = theTime;
    }

    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }
}