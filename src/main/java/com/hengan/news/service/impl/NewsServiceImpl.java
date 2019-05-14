package com.hengan.news.service.impl;

import com.hengan.news.config.LoginHelper;
import com.hengan.news.dao.NewsDAO;
import com.hengan.news.mapper.AccessLogMapper;
import com.hengan.news.mapper.NewsMapper;
import com.hengan.news.model.po.AccessLogPO;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserAuthKeyPO;
import com.hengan.news.model.vo.NewsVO;
import com.hengan.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by cyq on 2019/02/12.
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDAO newsDAO;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Override
    public NewsVO detail(Long id) {
        UserAuthKeyPO currentUser = LoginHelper.getCurrentUser();
        NewsVO detail = newsDAO.detail(id);
        if(detail != null && currentUser!=null){
            AccessLogPO accessLogPO = new AccessLogPO();
            accessLogPO.setNewsId(String.valueOf(id));
            accessLogPO.setWorkCode(currentUser.getWorkCode());
            accessLogMapper.insertSelective(accessLogPO);

        }
        return detail;
    }

    /**
     * 添加新闻
     * @param newsVO
     */
    @Override
    public void add(NewsVO newsVO) {
        newsDAO.add(newsVO);
    }

    /**
     * 新闻列表
     * @param newsVO
     * @return
     */
    @Override
    public List<NewsPO> selectPage(NewsVO newsVO) {
        return newsMapper.selectPage(newsVO);
    }

}
