package com.hengan.news.service.impl;

import com.hengan.news.core.AbstractService;
import com.hengan.news.dao.NewsMapper;
import com.hengan.news.dao.OperationMapper;
import com.hengan.news.model.po.News;
import com.hengan.news.model.po.Operation;
import com.hengan.news.service.NewsService;
import com.hengan.news.service.OperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by cyq on 2019/02/12.
 */
@Service
@Transactional
public class OperationServiceImpl extends AbstractService<Operation> implements OperationService {
    @Resource
    private OperationMapper operationMapper;



}
