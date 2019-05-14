package com.hengan.news.config;

import com.hengan.news.model.po.UserAuthKeyPO;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class LoginHelper {

    public static final ThreadLocal<UserAuthKeyPO> LOCAL_USER = new ThreadLocal<>();


    /**
     * 获取当前用户
     *
     * @return
     */
    public static UserAuthKeyPO getCurrentUser() {
        UserAuthKeyPO userInfo = LOCAL_USER.get();
        return userInfo;
    }

    /**
     * 清空当前用户
     */
    public static void clearUser() {
        LOCAL_USER.remove();
    }

    /**
     * 设置当前用户
     *
     * @param userInfo
     */
    public static void setCurrentUser(UserAuthKeyPO userInfo) {
        clearUser();
        LOCAL_USER.set(userInfo);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    public static UserAuthKeyPO getCurrentUser(HttpServletRequest request) {
        String authKey = request.getHeader("authKey");
        if (StringUtils.isEmpty(authKey) && null != request.getAttribute("authKey")) {
            authKey = (String) request.getAttribute("authKey");
        }
        if (StringUtils.isEmpty(authKey)) {
            return null;
        }
        return null;
    }
}
