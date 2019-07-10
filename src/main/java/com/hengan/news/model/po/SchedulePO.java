package com.hengan.news.model.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author cyq
 * @date 2019/7/10 10:53
 */
@Table(name = "tb_schedule")
public class SchedulePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private String picUrl;
    private String appCode;
    private String appName;
    private Integer sort;

    private Timestamp createTime;

    private Timestamp updateTime;

    @Column(name = "mark_for_delete")
    private Integer markForDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getMarkForDelete() {
        return markForDelete;
    }

    public void setMarkForDelete(Integer markForDelete) {
        this.markForDelete = markForDelete;
    }
}
