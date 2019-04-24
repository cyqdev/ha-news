package com.hengan.news.model.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author cyq
 */
@Table(name = "tb_operation")
public class OperationPO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY )
    private Long id;

    private String newsId;

    private BigInteger clickNum;

    private Timestamp createTime;

    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public BigInteger getClickNum() {
        return clickNum;
    }

    public void setClickNum(BigInteger clickNum) {
        this.clickNum = clickNum;
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
}
