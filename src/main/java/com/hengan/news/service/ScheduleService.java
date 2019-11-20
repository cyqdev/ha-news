package com.hengan.news.service;

import com.hengan.news.model.bean.ScheduleBean;
import com.hengan.news.model.po.ScheduleNewPO;
import com.hengan.news.model.vo.NewsMsgVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    /**
     * 新增一个定时任务
     */
    void addJob(ScheduleBean scheduleBean, ScheduleNewPO scheduleNewPO, List<NewsMsgVO> newsMsgVOList) ;

    /**
     * 暂停定时任务
     */
    void pauseJob(ScheduleBean scheduleBean);

    /**
     * 继续定时任务
     */
    void resumeJob(ScheduleBean scheduleBean);

    /**
     * 删除定时任务
     */
    void deleteJob(ScheduleBean scheduleBean);

    void findAllJobs();
}

