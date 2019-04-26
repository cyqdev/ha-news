package com.hengan.news.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hengan.news.common.util.EncryptUtil;
import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import com.hengan.news.core.Constant;
import com.hengan.news.dao.UserDAO;
import com.hengan.news.mapper.NewsMapper;
import com.hengan.news.mapper.UserMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.UserVO;
import com.hengan.news.schedule.UserSchedule;
import com.hengan.news.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by cyq on 2019/02/12.
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private UserDAO userDAO;


    @Override
    public UserPO findByWorkCode(String workCode) {
        UserPO byWorkCode = userDAO.findByWorkCode(workCode);
        NewsPO newsPO = userDAO.getNew("25040");
        return byWorkCode;
    }

    @Override
    public UserVO wxLogin(String code) {
        UserVO userVO = new UserVO();
        if(StringUtils.isBlank(WorkWXAPI.TOKEN)){
            UserSchedule.getAccessToken();
        }
        JSONObject userInfo = QYWXUtil.getUserInfo(code, WorkWXAPI.TOKEN);
        String workCode = "";
        try{
            workCode = userInfo.getString("UserId");
            if(StringUtils.isBlank(workCode)){
                return null;
            }
            UserPO user = new UserPO();
            user.setWorkCode(workCode);
            UserPO userPO = userMapper.selectOne(user);
            String authKey = EncryptUtil.encryptBase64(workCode,Constant.SECRET_KEY);
            userVO.setUserInfo(userPO);
            userVO.setAuthKey(authKey);
        }catch (Exception e){
            return null;
        }
        return userVO;
    }
}
