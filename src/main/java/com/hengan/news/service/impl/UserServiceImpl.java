package com.hengan.news.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hengan.news.common.util.EncryptUtil;
import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import com.hengan.news.core.Constant;
import com.hengan.news.dao.UserDAO;
import com.hengan.news.mapper.UserAuthKeyMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserAuthKeyPO;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.UserVO;
import com.hengan.news.schedule.UserSchedule;
import com.hengan.news.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


/**
 * Created by cyq on 2019/02/12.
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAuthKeyMapper userAuthKeyMapper;


    @Override
    public UserPO findByWorkCode(String workCode) {
        UserPO byWorkCode = userDAO.findByWorkCode(workCode);
        NewsPO newsPO = userDAO.getNew("25040");
        return byWorkCode;
    }

    @Override
    public UserVO wxLogin(String code) {
        UserVO userVO = new UserVO();
        if(StringUtils.isBlank(WorkWXAPI.NEWS_TOKEN)){
            UserSchedule.getAccessToken();
        }
//        System.out.println("开始获取用户信息###code:" + code + "  -------token:" + WorkWXAPI.NEWS_TOKEN);
        JSONObject userInfo = QYWXUtil.getUserInfo(code, WorkWXAPI.NEWS_TOKEN);
        System.out.println("获取到用户信息json:"+userInfo);
        String workCode = "";
        try{
            workCode = userInfo.getString("UserId");
            System.out.println("获取到用户ID:"+workCode);
            if(StringUtils.isBlank(workCode)){
                return null;
            }
            String authKey = EncryptUtil.encryptBase64(workCode,Constant.SECRET_KEY);
            System.out.println("生成的authKey"+authKey);
            System.out.println("开始获取人员信息"+authKey);
            UserPO userPO = userDAO.findByWorkCode(workCode);
            System.out.println("已获取人员信息"+userPO.getUserName());
            userVO.setUserInfo(userPO);
            userVO.setAuthKey(authKey);
            //处理添加访问人员authKey
            System.out.println("开始处理访问人员authkey");
            UserAuthKeyPO userAuthKeyPO = new UserAuthKeyPO();
            userAuthKeyPO.setAuthKey(authKey);
            UserAuthKeyPO authKeyPO = userAuthKeyMapper.selectOne(userAuthKeyPO);
            if(authKeyPO!=null) {
                System.out.println("更新访问人员:"+workCode+" authkey:"+authKey);
                authKeyPO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                userAuthKeyMapper.updateByPrimaryKeySelective(authKeyPO);
            }else {
                System.out.println("添加访问人员:"+workCode+"  authkey:"+authKey);
                userAuthKeyPO.setWorkCode(workCode);
                userAuthKeyPO.setUserName(userPO.getUserName());
                userAuthKeyMapper.insertSelective(userAuthKeyPO);
            }
        }catch (Exception e){
            System.out.println("出错啦");
            return null;
        }

        return userVO;
    }

    public static void main(String[] args) {
        String authKey = EncryptUtil.encryptBase64("17105223",Constant.SECRET_KEY);
        System.out.println(authKey);
    }

}
