package com.hengan.news.dao;

import com.hengan.news.core.Mapper;
import com.hengan.news.model.po.News;
import com.hengan.news.model.vo.NewsVO;

import java.util.List;

public interface NewsMapper extends Mapper<News> {
    List<News> selectPage1(NewsVO newsVO);
}