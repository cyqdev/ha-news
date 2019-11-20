package com.hengan.news.mapper;


import com.hengan.news.core.MyMapper;
import com.hengan.news.model.po.ScheduleNewPO;

import java.util.List;

public interface ScheduleNewMapper extends MyMapper<ScheduleNewPO> {

    //获取所有的定时  根据定时时间+应用 区分是否是同一个计划
    List<ScheduleNewPO> getAllSchedulePlan();
}
