package com.hengan.news.schedule;

import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserSchedule {

    @Scheduled(fixedRate=1000*60*59*2)//服务器启动时执行一次，之后每隔一个小时59分执行一次
    public void syncAccessToken() {
        getAccessToken();
    }
    public static void getAccessToken(){
        System.out.println("开始获取accessToken");
        String corpid = WorkWXAPI.CORPID;
        String corpsecret = WorkWXAPI.NEWS_SECRET;
        String accessToken = QYWXUtil.getAccessToken(corpid, corpsecret);
        if(StringUtils.isNotBlank(accessToken)){
            WorkWXAPI.NEWS_TOKEN = accessToken;
            System.out.println("已获取accessToken:"+accessToken);
        }
    }

    public static void main(String[] args) {
        getAccessToken();
    }

}
