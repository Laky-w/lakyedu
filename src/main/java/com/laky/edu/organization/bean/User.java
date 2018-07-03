package com.laky.edu.organization.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/11/20.
 */
public class User implements Serializable {
    private Integer id;
    private String userName; //用户名
    private String nickName; //昵称
    private String name;//真实姓名
    private String password;//密码
    private String headLogo;//头像
    private Integer theStatus;//状态
    private Integer branchId;
    private Branch branch;//机构
    private Integer schoolZoneId;
    private SchoolZone schoolZone;//校区
    private Date createDatetime;//创建时间
    private String phone;//联系方式
    private String email;//邮箱
    private Integer sex;//性别
    private Date birthday;//生日
    private Integer isSuper;//是否超级管理员 1是 2 否
    private String idCard; //身份证
    private Integer quitStatus;//离职状态 1 在职 2离职
    private Date quitDate;//离职状态
    private String quitRemarks;//离职原因
    private List<UserRole> userRoleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadLogo() {
        return headLogo;
    }

    public void setHeadLogo(String headLogo) {
        this.headLogo = headLogo;
    }

    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }


    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Integer getSchoolZoneId() {
        return schoolZoneId;
    }

    public void setSchoolZoneId(Integer schoolZoneId) {
        this.schoolZoneId = schoolZoneId;
    }

    public SchoolZone getSchoolZone() {
        return schoolZone;
    }

    public void setSchoolZone(SchoolZone schoolZone) {
        this.schoolZone = schoolZone;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Integer isSuper) {
        this.isSuper = isSuper;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public Integer getQuitStatus() {
        return quitStatus;
    }

    public void setQuitStatus(Integer quitStatus) {
        this.quitStatus = quitStatus;
    }

    public Date getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(Date quitDate) {
        this.quitDate = quitDate;
    }

    public String getQuitRemarks() {
        return quitRemarks;
    }

    public void setQuitRemarks(String quitRemarks) {
        this.quitRemarks = quitRemarks;
    }
}
