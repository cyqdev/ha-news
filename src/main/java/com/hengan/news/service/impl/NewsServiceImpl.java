package com.hengan.news.service.impl;

import com.hengan.news.mapper.NewsMapper;
import com.hengan.news.mapper.OperationMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.OperationPO;
import com.hengan.news.model.vo.NewsVO;
import com.hengan.news.service.NewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * Created by cyq on 2019/02/12.
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private OperationMapper operationMapper;

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
}
