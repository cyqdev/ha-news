package com.hengan.news.service.impl;

import com.hengan.news.core.AbstractService;
import com.hengan.news.dao.NewsMapper;
import com.hengan.news.dao.OperationMapper;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.po.OperationPO;
import com.hengan.news.model.vo.NewsVO;
import com.hengan.news.service.NewsService;
import com.hengan.news.service.UserService;
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
public class UserServiceImpl extends AbstractService<UserPO> implements UserService {

}
