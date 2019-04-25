package com.hengan.news.service.impl;

import com.hengan.news.dao.UserDAO;
import com.hengan.news.mapper.UserMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.NewsVO;
import com.hengan.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by cyq on 2019/02/12.
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserDAO userDAO;


    @Override
    public UserPO findByWorkCode(String workCode) {
//        DatabaseContextHolder.setDatabaseType(DatabaseType.userdb);
        UserPO byWorkCode = userDAO.findByWorkCode(workCode);
        NewsPO newsPO = userDAO.findNews(workCode);
        return byWorkCode;
    }

    @Override
    public UserPO wxLogin(String code) {
        return null;
    }
}
