package com.laky.edu.reception.web.form;

import java.math.BigDecimal;

/**
 * Created by 湖之教育工作室·laky on 2018/2/2.
 */
public class OrderMoneyRecordForm {
    private Integer id;//订单id
    private Integer accountId;//账户id
    private BigDecimal money;//实收
    private BigDecimal receivable;//实收
    private BigDecimal subtractMoney;//抹零
    private Integer salesmanId;//销售id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
    }
}
