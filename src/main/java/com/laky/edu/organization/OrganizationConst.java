package com.laky.edu.organization;

/**
 * 机构状态类
 * Created by 湖之教育工作室·laky on 2017/11/20.
 */
public class OrganizationConst {
    /*
     *机构状态
     */
    public static int BRANCH_STATUS_NORMAL = 1;//正常
    public static int BRANCH_STATUS_SEALUP = 2;//封存
    public static int BRANCH_STATUS_DELETE = 0;//删除
    /*
     *校区状态
     */
    public static int SCHOOL_ZONE_STATUS_NORMAL = 1;//正常
    public static int SCHOOL_ZONE_STATUS_SEALUP = 2;//封存
    public static int SCHOOL_ZONE_STATUS_DELETE = 0;//删除
    /*
     * 校区类型
     */
    public static int SCHOOL_ZONE_TYPE_HQ = 1;//总部
    public static int SCHOOL_ZONE_TYPE_CHILD = 2;//分校
    public static int SCHOOL_ZONE_TYPE_DEPARTMENT = 3;//部门

    /*
     * 用户状态
     */
    public static int USER_STATUS_NORMAL = 1;//正常
    public static int USER_STATUS_DELETE = 0;//删除
    public static int USER_STATUS_SEALUP = 2;//禁用

    /*
     * 用户类型
     */
    public static int USER_SUPER_YES = 1;//超级管理
    public static int USER_SUPER_NO = 2;//普通用户


    public static int NOTICE_BRANCH = 1 ;//机构公告
    public static int NOTICE_SCHOOL = 2;//校区公告
    public static int NOTICE_TOTAL = 3;//全站公告

}
