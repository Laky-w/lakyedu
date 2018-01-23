package com.laky.edu.logistics.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsRecord implements Serializable {
    private Integer id;

    private Integer goodsId;

    private Integer schoolZoneId;

    private Integer schoolZoneIdIn;//转入校区

    private Integer theType;

    private Integer amount;

    private Integer userId;

    private Date createTime;

    private BigDecimal price;

    private String otherName;

    private Integer supplierId;

    private Date returnDate;

    private Integer returnStatus;

    private Integer oldAmount;//原始库存

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }

    public Integer getTheType() {
        return theType;
    }

    public void setTheType(Integer theType) {
        this.theType = theType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName == null ? null : otherName.trim();
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Integer getSchoolZoneIdIn() {
        return schoolZoneIdIn;
    }

    public void setSchoolZoneIdIn(Integer schoolZoneIdIn) {
        this.schoolZoneIdIn = schoolZoneIdIn;
    }

    public Integer getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(Integer oldAmount) {
        this.oldAmount = oldAmount;
    }
}