package com.hengan.news.schedule;

import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import com.hengan.news.core.Constant;
import com.hengan.news.mapper.ScheduleMapper;
import com.hengan.news.model.po.SchedulePO;
import com.hengan.news.model.vo.NewsMsgVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Cyq
 * @Date 2019/4/30 10:30
 **/
@Component
public class SendNewsMsgSchedule {

    @Autowired
    private ScheduleMapper scheduleMapper;


    /**
     * 运营信息发布应用每日推送（根据标签）
     * 标签:日报（全部）tagid: 9
     */
    @Scheduled(cron = "0 0 8 ? * *")
    public void sendMessage() {
        SendOperationInfo(Constant.APP_CODE.SJZX);
        SendOperationInfo(Constant.APP_CODE.YYXXFB);
        SendOperationInfo(Constant.APP_CODE.MDXXWH);
        SendOperationInfo(Constant.APP_CODE.XWXXFB);
    }

    /**
     * 运营信息发布应用每日推送（根据标签）
     * 标签:日报（全部）tagid: 9
     */
    public void SendOperationInfo(String appCode) {
        System.out.println("开始获取accessToken");
        Map<String, Object> map = getTokenByType(appCode);
        String accessToken = (String) map.get("accessToken");
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date yesterday = c.getTime();
        //取前一天
        String yesterdayStr = format.format(yesterday);
        System.out.println(yesterdayStr);
        //查询待推送列表
        List<NewsMsgVO> list = new ArrayList<>();
//        List<SchedulePO> poList = scheduleMapper.selectAll();
        Example example = new Example(SchedulePO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("markForDelete", 0);
        criteria.andEqualTo("appCode", appCode);
        example.setOrderByClause("sort asc");
        List<SchedulePO> poList = scheduleMapper.selectByExample(example);
        for (int i = 0; i < poList.size(); i++) {
            SchedulePO po = poList.get(i);
            NewsMsgVO newsMsgVO = new NewsMsgVO();
            BeanUtils.copyProperties(po, newsMsgVO);
            newsMsgVO.setDescription(po.getTitle());
            newsMsgVO.setPicurl(po.getPicUrl());
            if (i == 0) {
                newsMsgVO.setTitle(po.getTitle() + "_" + yesterdayStr);
            }
            list.add(newsMsgVO);
        }
        if(!list.isEmpty() ){
            //以标签发送  日报（全部）tagid: 9
            QYWXUtil.sendnNewsMessage(accessToken, null, null, "9", (Integer) map.get("agentId"), list);
//            QYWXUtil.sendnNewsMessage(accessToken, "17105223|010944", null, null,(Integer) map.get("agentId"), list);
        }
    }

    public Map<String, Object> getTokenByType(String appCode) {
        String corpid, corpsecret, accessToken = "";
        Map<String, Object> map = new HashMap<>();
        map.put("agentId",null);
        corpid = WorkWXAPI.CORPID;
        switch (appCode) {
            case Constant.APP_CODE.SJZX:
                corpsecret = WorkWXAPI.DATA_SECRET;
                map.put("agentId", WorkWXAPI.DATA_AGENTID);
                accessToken = QYWXUtil.getAccessToken(corpid, corpsecret);
                if (StringUtils.isNotBlank(accessToken)) {
                    WorkWXAPI.DATA_TOKEN = accessToken;
                    System.out.println("已获取accessToken:" + accessToken);
                }
                break;
            case Constant.APP_CODE.YYXXFB:
                corpsecret = WorkWXAPI.OPERATION_SECRET;
                map.put("agentId", WorkWXAPI.OPERATION_AGENTID);
                accessToken = QYWXUtil.getAccessToken(corpid, corpsecret);
                if (StringUtils.isNotBlank(accessToken)) {
                    WorkWXAPI.OPERATION_TOKEN = accessToken;
                    System.out.println("已获取accessToken:" + accessToken);
                }
                break;
            default:
                break;
        }
        map.put("accessToken",accessToken);
        return map;
    }

}
