package com.hengan.news.dao.impl;

import com.hengan.news.dao.UserDAO;
import com.hengan.news.mapper.NewsMapper;
import com.hengan.news.mapper.UserMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author Cyq
 * @Date 2019/4/25 10:51
 **/
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public UserPO findByWorkCode(String workCode) {
        UserPO userPO = new UserPO();
        userPO.setWorkCode(workCode);
        UserPO userPO1 = userMapper.selectOne(userPO);
        return userPO1;
    }

    @Override
    public NewsPO findNews(String workCode) {
        NewsPO news = newsMapper.selectByPrimaryKey("25040");
        return news;
    }
}
