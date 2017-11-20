package com.laky.edu.organization;

/**
 * 机构状态类
 * Created by 湖之教育工作室·laky on 2017/11/20.
 */
public class OrganizationConst {
    /*
    机构状态
     */
    public static int BRANCH_STATUS_NORMAL = 1;//正常
    public static int BRANCH_STATUS_SEALUP = 2;//封存
    public static int BRANCH_STATUS_DELETE = 0;//删除
     /*
    校区状态
     */
    public static int SCHOOL_ZONE_STATUS_NORMAL = 1;//正常
    public static int SCHOOL_ZONE_STATUS_SEALUP = 2;//封存
    public static int SCHOOL_ZONE_STATUS_DELETE = 0;//删除
    /*
   校区类型
   */
    public static int SCHOOL_ZONE_TYPE_HQ = 1;//总部
    public static int SCHOOL_ZONE_TYPE_CHILD = 2;//分校
    public static int SCHOOL_ZONE_TYPE_DEPARTMENT = 3;//部门
}
