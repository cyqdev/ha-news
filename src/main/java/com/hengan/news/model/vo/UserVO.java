package com.hengan.news.model.vo;

import com.hengan.news.model.po.UserPO;

/**
 * @Author Cyq
 * @Date 2019/4/26 11:26
 **/
public class UserVO {

    private UserPO userInfo;

    private String authKey;

    public UserPO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserPO userInfo) {
        this.userInfo = userInfo;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
