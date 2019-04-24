package com.hengan.news.service.impl;

import com.hengan.news.dao.NewsMapper;
import com.hengan.news.dao.OperationMapper;
import com.hengan.news.model.po.News;
import com.hengan.news.model.po.Operation;
import com.hengan.news.model.vo.NewsVO;
import com.hengan.news.service.NewsService;
import com.hengan.news.core.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * Created by cyq on 2019/02/12.
 */
@Service
@Transactional
public class NewsServiceImpl extends AbstractService<News> implements NewsService {
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private OperationMapper operationMapper;

    @Override
    public NewsVO detail(Long id) {
        News news = newsMapper.selectByPrimaryKey(id);
        NewsVO newsVO = new NewsVO();
        if(news!=null){
            BeanUtils.copyProperties(news,newsVO);
            Operation operation = new Operation();
            operation.setNewsId(String.valueOf(news.getId()));
            Operation one = operationMapper.selectOne(operation);
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
