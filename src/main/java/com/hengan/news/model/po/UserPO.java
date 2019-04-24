package com.hengan.news.model.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user")
public class UserPO {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "work_code")
    private String workCode;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "sub_company")
    private String subCompany;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_ids")
    private String departmentIds;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "post" )
    private String post;

    @Column(name = "weixin")
    private String weixin;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "wx_status")
    private Integer wxStatus;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getSubCompany() {
        return subCompany;
    }

    public void setSubCompany(String subCompany) {
        this.subCompany = subCompany;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getWxStatus() {
        return wxStatus;
    }

    public void setWxStatus(Integer wxStatus) {
        this.wxStatus = wxStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
