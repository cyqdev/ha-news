package com.hengan.news.dao;

import com.hengan.news.core.MyMapper;
import com.hengan.news.model.po.News;
import com.hengan.news.model.vo.NewsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper extends MyMapper<News> {
    List<News> selectPage1(NewsVO newsVO);
}