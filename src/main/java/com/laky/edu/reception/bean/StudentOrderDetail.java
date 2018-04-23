package com.laky.edu.reception.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class StudentOrderDetail implements Serializable {
    private Integer id;

    private Integer orderId;

    private Integer courseId;

    private Integer classId;

    private Integer theType;

    private Integer itemType;

    private BigDecimal number;

    private BigDecimal price;

    private BigDecimal discount;

    private BigDecimal sellPrice;

    private BigDecimal total;

    private Integer schoolId;//校区id 前台表单字段（报名时）

    private BigDecimal subtractPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTheType() {
        return theType;
    }

    public void setTheType(Integer theType) {
        this.theType = theType;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public BigDecimal getSubtractPrice() {
        return subtractPrice;
    }

    public void setSubtractPrice(BigDecimal subtractPrice) {
        this.subtractPrice = subtractPrice;
    }
}