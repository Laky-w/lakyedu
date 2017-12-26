package com.laky.edu.logistics.bean;

import java.io.Serializable;

public class GoodsRepository implements Serializable {
    private Integer id;

    private Integer goodsId;

    private Integer schoolZoneId;

    private Integer lastAmount;

    private Integer consumeAmount;

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

    public Integer getLastAmount() {
        return lastAmount;
    }

    public void setLastAmount(Integer lastAmount) {
        this.lastAmount = lastAmount;
    }

    public Integer getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(Integer consumeAmount) {
        this.consumeAmount = consumeAmount;
    }


}