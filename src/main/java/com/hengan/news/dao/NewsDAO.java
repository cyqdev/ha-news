package com.hengan.news.dao;

import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.vo.NewsVO;

import java.util.List;

/**
 * @Author Cyq
 * @Date 2019/4/26 14:17
 **/
public interface NewsDAO {

    /**
     * 新闻详情
     * @param id
     * @return
     */
    NewsVO detail(Long id);

    /**
     * 新闻添加
     * @param newsVO
     */
    void add(NewsVO newsVO);

}
