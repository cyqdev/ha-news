package com.hengan.news.schedule;

import com.hengan.news.common.util.DateTimeUtils;
import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import com.hengan.news.model.vo.NewsMsgVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author Cyq
 * @Date 2019/4/30 10:30
 **/
@Component
public class SendNewsMsgSchedule {

    /**
     * 运营信息发布应用每日推送（根据标签）
     * 标签:日报（全部）tagid: 9
     */
    @Scheduled(cron = "0 0 8 ? * *")
    static void SendOperationInfo(){

       System.out.println("开始获取accessToken");
       String corpid = WorkWXAPI.CORPID;
       String corpsecret = WorkWXAPI.OPERATION_SECRET;
       String accessToken = QYWXUtil.getAccessToken(corpid, corpsecret);
       if(StringUtils.isNotBlank(accessToken)){
           WorkWXAPI.OPERATION_TOKEN = accessToken;
           System.out.println("已获取accessToken:"+accessToken);
       }

       SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
       Calendar c = Calendar.getInstance();
       c.setTime(new Date());
       c.add(Calendar.DATE, -1);
       Date yesterday = c.getTime();
       String yesterday_str= format.format(yesterday);//前一天
       System.out.println(yesterday_str);

       List<NewsMsgVO> list = new ArrayList<>();
       NewsMsgVO newsMsgVO = new NewsMsgVO();
       newsMsgVO.setTitle("品类销售收入_"+ yesterday_str);
       newsMsgVO.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbaaa22057237762a&redirect_uri=http%3a%2f%2ffr.hengan.cn%3a8080%2fWeiXinServer%3fop%3dh5%26formlet%3dhawx%2fyyxxfb%2fQ-FHSRY_WX1.frm&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
       newsMsgVO.setPicurl("http://hengan-mall.oss-cn-shenzhen.aliyuncs.com/mall/img/1557817429123.jpg");
       newsMsgVO.setDescription("这是描述1~~~~~~~~~~");

       NewsMsgVO newsMsgVO1 = new NewsMsgVO();
       newsMsgVO1.setTitle("渠道销售收入");
       newsMsgVO1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbaaa22057237762a&redirect_uri=http%3a%2f%2ffr.hengan.cn%3a8080%2fWeiXinServer%3fop%3dh5%26reportlet%3dhawx%2fyyxxfb%2fQ-QDSRY_WX2.cpt%26__bypagesize__%3dfalse&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
       newsMsgVO1.setPicurl("http://hengan-mall.oss-cn-shenzhen.aliyuncs.com/mall/img/1557817451858.jpg");
       newsMsgVO1.setDescription("这是描述2~~~~~~~~~~");

       NewsMsgVO newsMsgVO2 = new NewsMsgVO();
       newsMsgVO2.setTitle("品类渠道销售收入");
       newsMsgVO2.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbaaa22057237762a&redirect_uri=http%3a%2f%2ffr.hengan.cn%3a8080%2fWeiXinServer%3fop%3dh5%26formlet%3dhawx%2fyyxxfb%2fQ-PLQDSRY_WX2.frm&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
       newsMsgVO2.setPicurl("http://hengan-mall.oss-cn-shenzhen.aliyuncs.com/mall/img/1557817466151.jpg");
       newsMsgVO2.setDescription("这是描述3~~~~~~~~~~");

       NewsMsgVO newsMsgVO3 = new NewsMsgVO();
       newsMsgVO3.setTitle("区域和巴销售收入");
       newsMsgVO3.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbaaa22057237762a&redirect_uri=http%3a%2f%2ffr.hengan.cn%3a8080%2fWeiXinServer%3fop%3dh5%26formlet%3dhawx%2fyyxxfb%2fQ-QYAMBSR.frm&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
       newsMsgVO3.setPicurl("http://hengan-mall.oss-cn-shenzhen.aliyuncs.com/mall/img/1557817480277.jpg");
       newsMsgVO3.setDescription("这是描述4~~~~~~~~~~");

       NewsMsgVO newsMsgVO4 = new NewsMsgVO();
       newsMsgVO4.setTitle("重点关注系列销售收入及库存数据");
       newsMsgVO4.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbaaa22057237762a&redirect_uri=http%3a%2f%2ffr.hengan.cn%3a8080%2fWeiXinServer%3fop%3dh5%26formlet%3dhawx%2fyyxxfb%2fQ-FOCUS_WX.frm&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
       newsMsgVO4.setPicurl("http://hengan-mall.oss-cn-shenzhen.aliyuncs.com/mall/img/1557817493686.jpg");
       newsMsgVO4.setDescription("这是描述5~~~~~~~~~~");

       NewsMsgVO newsMsgVO5 = new NewsMsgVO();
       newsMsgVO5.setTitle("成品库存情况（四大品类）");
       newsMsgVO5.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbaaa22057237762a&redirect_uri=http%3a%2f%2ffr.hengan.cn%3a8080%2fWeiXinServer%3fop%3dh5%26formlet%3dhawx%2fyyxxfb%2fQ-KUCUN_WX.frm&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
       newsMsgVO5.setPicurl("http://hengan-mall.oss-cn-shenzhen.aliyuncs.com/mall/img/1557817503587.jpg");
       newsMsgVO5.setDescription("这是描述6~~~~~~~~~~");

       list.add(newsMsgVO);
       list.add(newsMsgVO1);
       list.add(newsMsgVO2);
       list.add(newsMsgVO3);
       list.add(newsMsgVO4);
       list.add(newsMsgVO5);
        //以标签发送  日报（全部）tagid: 9
       QYWXUtil.sendnNewsMessage(accessToken,null,null,"9",WorkWXAPI.OPERATION_AGENTID,list);
   }

    public static void main(String[] args) {
        //SendOperationInfo();

    }

}
