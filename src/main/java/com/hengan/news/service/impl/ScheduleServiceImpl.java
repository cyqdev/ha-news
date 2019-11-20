package com.hengan.news.service.impl;


import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import com.hengan.news.model.bean.ScheduleBean;
import com.hengan.news.model.po.ScheduleNewPO;
import com.hengan.news.model.vo.NewsMsgVO;
import com.hengan.news.schedule.job.WxSendMsgJob;
import com.hengan.news.service.ScheduleService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void addJob(ScheduleBean scheduleBean, ScheduleNewPO scheduleNewPO, List<NewsMsgVO> newsMsgVOList) {
        try {
            //先获取acess_token
            String corpid = WorkWXAPI.CORPID;
            String corpsecret = scheduleNewPO.getAppSecret();
            String accessToken = QYWXUtil.getAccessToken(corpid, corpsecret);
            if (accessToken == null || "".equals(accessToken)) {
                throw new Exception();
            }
            //放置参数
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("accessToken", accessToken);
            jobDataMap.put("touser", scheduleNewPO.getTouser());
            jobDataMap.put("toparty", scheduleNewPO.getToparty());
            jobDataMap.put("totag", scheduleNewPO.getTotag());
            jobDataMap.put("agentid", scheduleNewPO.getAppAgentid());
            jobDataMap.put("mpnewsVOS", newsMsgVOList);

            JobDetail jobDetail = JobBuilder.newJob(WxSendMsgJob.class).setJobData(jobDataMap)
                    .withIdentity(scheduleBean.getJobName(), scheduleBean.getJobGroup())
                    .build();

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleBean.getJobName(), scheduleBean.getJobGroup())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleNewPO.getCornExpression()))
                    .build();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 暂停定时任务
     */
    @Override
    public void pauseJob(ScheduleBean scheduleBean) {
        try {
            scheduler.pauseJob(JobKey.jobKey(scheduleBean.getJobName(), scheduleBean.getJobGroup()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 继续定时任务
     */
    @Override
    public void resumeJob(ScheduleBean scheduleBean) {
        try {
            scheduler.resumeJob(JobKey.jobKey(scheduleBean.getJobName(), scheduleBean.getJobGroup()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除定时任务
     */
    @Override
    public void deleteJob(ScheduleBean scheduleBean) {
        try {
            scheduler.pauseJob(JobKey.jobKey(scheduleBean.getJobName(), scheduleBean.getJobGroup()));
            scheduler.deleteJob(JobKey.jobKey(scheduleBean.getJobName(), scheduleBean.getJobGroup()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询所有定时
     */
    @Override
    public void findAllJobs() {
        try {
            Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.anyGroup());
            for (TriggerKey triggerKey : triggerKeys) {
                JobDataMap jobDataMap = scheduler.getTrigger(triggerKey).getJobDataMap();
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
