package com.hengan.news.model.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author Cyq
 * @Date 2019/4/30 11:25
 **/
@Table(name = "tb_attachment")
public class AttachmentPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String picUrl;

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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
