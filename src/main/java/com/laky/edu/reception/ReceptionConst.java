package com.laky.edu.reception;

/**
 * Created by 湖之教育工作室·laky on 2017/12/27.
 */
public class ReceptionConst {
    /**
     * 客户类型
     */
    public static int CUSTOMER_TYPE_YES=1; //未分配
    public static int CUSTOMER_TYPE_NO=2; //已分配
//    public static int CUSTOMER_TYPE_STATUS_NO=1; //未更近

    /**
     * 学生报班状态
     */
    public static int STUDENT_CLASS_STATUS_NO=1; //未报班
    public static int STUDENT_CLASS_STATUS_YES=2; //已报班
    /**
     * 学生班级状态
     */
    public static int STUDENT_CLASS_STATUS_STUDYING=1; //在读
    public static int STUDENT_CLASS_STATUS_WAIT=2; //待分班
//    public static int STUDENT_CLASS_STATUS_YES=2; //已报班
    /**
     * 订单费用状态
     */
    public static int ORDER_COST_STATUS_COMPLETE = 1;//已收齐
    public static int ORDER_COST_STATUS_ARREARS =2;//未收齐
    /**
     * 订单类型
     */
    public static int ORDER_TYPE_CHARGE = 1;//收费
    public static int ORDER_TYPE_REFUND =2;//欠费

}
