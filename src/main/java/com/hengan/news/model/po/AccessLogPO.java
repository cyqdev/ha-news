package com.hengan.news.model.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author Cyq
 * @Date 2019/4/28 15:27
 **/
@Table(name = "tb_access_log")
public class AccessLogPO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY )
    private Long id;

    private String workCode;

    private String newsId;

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

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
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
