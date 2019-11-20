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

        QYWXUtil.sendnNewsMessage(jobDataMap.getString("accessToken"), jobDataMap.getString("touser"), jobDataMap.getString("toparty"), jobDataMap.getString("totag"), jobDataMap.getInt("agentid"), (List<NewsMsgVO>) jobDataMap.get("mpnewsVOS"));

    }
}
