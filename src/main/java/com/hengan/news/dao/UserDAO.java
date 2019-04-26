package com.hengan.news.dao;

import com.hengan.news.annotation.DS;
import com.hengan.news.config.datasource.DatabaseType;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.UserVO;

/**
 * @Author Cyq
 * @Date 2019/4/26 14:17
 **/
public interface UserDAO  {

    @DS(DatabaseType.userdb)
    UserPO findByWorkCode(String workCode);

    NewsPO getNew(String code);

    UserVO wxLogin(String code);

}
