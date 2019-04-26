package com.hengan.news.service;

import com.hengan.news.annotation.DS;
import com.hengan.news.config.datasource.DatabaseType;
import com.hengan.news.core.MyMapper;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.UserVO;


/**
 * Created by cyq on 2019/02/12.
 */
public interface UserService {

    UserPO findByWorkCode(String workCode);

    UserVO wxLogin(String code);

}
