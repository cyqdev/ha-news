package com.hengan.news.service;

import com.hengan.news.annotation.DS;
import com.hengan.news.datasource.DatabaseType;
import com.hengan.news.model.po.UserPO;


/**
 * Created by cyq on 2019/02/12.
 */
public interface UserService {

    UserPO findByWorkCode(String workCode);

    UserPO wxLogin(String code);
}
