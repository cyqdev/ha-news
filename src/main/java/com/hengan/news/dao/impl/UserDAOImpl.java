package com.hengan.news.dao.impl;

import com.hengan.news.dao.UserDAO;
import com.hengan.news.mapper.NewsMapper;
import com.hengan.news.mapper.UserMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author Cyq
 * @Date 2019/4/26 14:17
 **/
@Repository
public  class UserDAOImpl implements UserDAO {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public UserPO findByWorkCode(String workCode) {
        System.out.println(11111);
        return userMapper.selectByPrimaryKey("17105223");
    }

    @Override
    public NewsPO getNew(String code) {
        System.out.println(22222);
        return newsMapper.selectByPrimaryKey("25040");
    }

    @Override
    public UserVO wxLogin(String code) {
        return null;
    }
}
