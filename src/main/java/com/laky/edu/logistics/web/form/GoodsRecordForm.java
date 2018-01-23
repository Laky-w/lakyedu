package com.laky.edu.logistics.web.form;

import com.laky.edu.logistics.bean.GoodsRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/12/29.
 */
public class GoodsRecordForm {
   private Date createTime;
   private Integer supplierId;
   private List<GoodsRecord> goodsList;
   private String otherName;
   private Integer oldAmount;
   private Date returnDate;
   private Integer schoolZoneId;
   private Integer returnStatus;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public List<GoodsRecord> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsRecord> goodsList) {
        this.goodsList = goodsList;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
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


    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }
}
