package com.hengan.news.schedule;

import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserSchedule {

    @Scheduled(cron = "0/7000 * * * * ?") // 每7000秒执行一次
    public void syncAccessToken() {
        getAccessToken();
    }
    public static void getAccessToken(){
        System.out.println("开始获取accessToken");
        String corpid = WorkWXAPI.CORPID;
        String corpsecret = WorkWXAPI.AUTH_APP_SECRET;
        String accessToken = QYWXUtil.getAccessToken(corpid, corpsecret);
        if(StringUtils.isNotBlank(accessToken)){
            WorkWXAPI.TOKEN = accessToken;
        }
    }

}
