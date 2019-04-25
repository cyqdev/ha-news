package com.hengan.news.service.impl;

import com.hengan.news.dao.OperationMapper;
import com.hengan.news.model.po.OperationPO;
import com.hengan.news.service.OperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by cyq on 2019/02/12.
 */
@Service
@Transactional
public class OperationServiceImpl implements OperationService {
    @Resource
    private OperationMapper operationMapper;



}
