package com.hengan.news.mapper;

import com.hengan.news.core.MyMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.vo.NewsVO;

import java.util.List;

public interface NewsMapper extends MyMapper<NewsPO> {
    List<NewsPO> selectPage1(NewsVO newsVO);
}