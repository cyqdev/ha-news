package com.hengan.news.dao.impl;

import com.hengan.news.dao.NewsDAO;
import com.hengan.news.dao.UserDAO;
import com.hengan.news.mapper.NewsMapper;
import com.hengan.news.mapper.OperationMapper;
import com.hengan.news.mapper.UserAuthKeyMapper;
import com.hengan.news.mapper.UserMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.OperationPO;
import com.hengan.news.model.po.UserAuthKeyPO;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.NewsVO;
import com.hengan.news.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @Author Cyq
 * @Date 2019/4/26 14:17
 **/
@Repository
public class NewsDAOImpl implements NewsDAO {

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private OperationMapper operationMapper;

    /**
     * 新闻详情
     * @param id
     * @return
     */
    @Override
    public NewsVO detail(Long id) {
        NewsPO news = newsMapper.selectByPrimaryKey(id);
        NewsVO newsVO = new NewsVO();
        if(news!=null){
            BeanUtils.copyProperties(news,newsVO);
            OperationPO operation = new OperationPO();
            operation.setNewsId(String.valueOf(news.getId()));
            OperationPO one = operationMapper.selectOne(operation);
            if(one!=null){
                one.setClickNum(one.getClickNum().add(new BigInteger("1")));
                one.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                operationMapper.updateByPrimaryKey(one);
                newsVO.setClickNum(one.getClickNum());
            }else {
                operation.setClickNum(new BigInteger("1"));
                operation.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                operationMapper.insertSelective(operation);
                newsVO.setClickNum(operation.getClickNum());
            }
        }
        return newsVO;
    }

    @Override
    public void add(NewsVO newsVO) {
        NewsPO newsPO = new NewsPO();
        BeanUtils.copyProperties(newsVO,newsPO);
        this.newsMapper.insertSelective(newsPO);
    }
}
