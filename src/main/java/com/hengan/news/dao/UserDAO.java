package com.hengan.news.dao;

import com.hengan.news.annotation.DS;
import com.hengan.news.config.datasource.DatabaseType;
import com.hengan.news.core.MyMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserAuthKeyPO;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.UserVO;

/**
 * @Author Cyq
 * @Date 2019/4/26 14:17
 **/
public interface UserDAO  {

    /**
     * 根据工号查询用户信息
     * @param workCode
     * @return
     */
    @DS(DatabaseType.userdb)
    UserPO findByWorkCode(String workCode);

    /**
     * 微信授权登录
     * @param code
     * @return
     */
    UserVO wxLogin(String code);

    /**
     * 根据authkey查询用户基本信息
     * @param authkey
     * @return
     */
    UserAuthKeyPO selectUserInfoByAuthKey(String authkey);

    /**
     * 测试接口
     * @param code
     * @return
     */
    NewsPO getNew(String code);

}
