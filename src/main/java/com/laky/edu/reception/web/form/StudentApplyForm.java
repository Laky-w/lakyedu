package com.laky.edu.reception.web.form;

import com.laky.edu.finance.bean.MoneyRecordAccount;
import com.laky.edu.reception.bean.StudentOrder;
import com.laky.edu.reception.bean.StudentOrderDetail;

import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/12/27.
 */
public class StudentApplyForm {
    private StudentOrder bill;
    private List<StudentOrderDetail> chargeDetails;
    private List<MoneyRecordAccount> financeAccount;

    public StudentOrder getBill() {
        return bill;
    }

    public void setBill(StudentOrder bill) {
        this.bill = bill;
    }

    public List<StudentOrderDetail> getChargeDetails() {
        return chargeDetails;
    }

    public void setChargeDetails(List<StudentOrderDetail> chargeDetails) {
        this.chargeDetails = chargeDetails;
    }

    public List<MoneyRecordAccount> getFinanceAccount() {
        return financeAccount;
    }

    public void setFinanceAccount(List<MoneyRecordAccount> financeAccount) {
        this.financeAccount = financeAccount;
    }
}
