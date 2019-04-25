package com.hengan.news.dao;

import com.hengan.news.annotation.DS;
import com.hengan.news.datasource.DatabaseType;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserPO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author Cyq
 * @Date 2019/4/25 10:51
 **/
@Repository
public interface UserDAO {

    @DS(DatabaseType.userdb)
    UserPO findByWorkCode(String workCode);

//    @DS(DatabaseType.newsdb)
    NewsPO findNews(String workCode);
}
