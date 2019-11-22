package com.hengan.news.schedule.job;

import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import com.hengan.news.model.vo.NewsMsgVO;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class WxSendMsgJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();

        //先获取
        String corpid = WorkWXAPI.CORPID;
        String appSecret = jobDataMap.getString("appSecret");
        String accessToken = QYWXUtil.getAccessToken(corpid, appSecret);
        if (accessToken == null || "".equals(accessToken)) {
            throw new JobExecutionException();
        }

        QYWXUtil.sendnNewsMessage(accessToken, jobDataMap.getString("touser"), jobDataMap.getString("toparty"), jobDataMap.getString("totag"), jobDataMap.getInt("agentid"), (List<NewsMsgVO>) jobDataMap.get("mpnewsVOS"));

    }
}
