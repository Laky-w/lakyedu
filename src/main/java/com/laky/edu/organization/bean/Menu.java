package com.laky.edu.organization.bean;

import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/11/24.
 */
public class Menu {
    private Integer id;
    private String title;
    private String icon;
    private String index;
    private Integer parentId;
    private String url;
    private Integer sort;
    private List<Menu> subs; //下级菜单
    private List<Authority> authorities; //菜单权限

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIndex() {
        this.index = this.url;
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Menu> getSubs() {
        return subs;
    }

    public void setSubs(List<Menu> subs) {
        this.subs = subs;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
