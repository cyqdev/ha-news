package com.hengan.news.service;

import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.vo.NewsVO;


/**
 * Created by cyq on 2019/02/12.
 */
public interface NewsService {

     NewsVO detail(Long id);
}
