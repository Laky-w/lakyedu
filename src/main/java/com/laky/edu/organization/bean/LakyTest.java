package com.laky.edu.organization.bean;

import java.util.Date;

/**
 * Created by 95 on 2017/11/14.
 */
public class LakyTest {
    private Integer id;
    private String name;
    private Integer sex;
    private String sexName;
    private Date birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSexName() {
        if (this.sex == null || this.sex==1 ){
            return "男";
        } else {
            return "女";
        }
    }
}
