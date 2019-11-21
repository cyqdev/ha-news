package com.hengan.news.schedule;

import com.hengan.news.mapper.ScheduleNewMapper;
import com.hengan.news.model.bean.ScheduleBean;
import com.hengan.news.model.po.ScheduleNewPO;
import com.hengan.news.model.vo.NewsMsgVO;
import com.hengan.news.service.ScheduleService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author Ypj
 * @Date 2019/11/11 11:11
 **/
@Component
public class SendNewsMsgNewSchedule {

    @Autowired
    private ScheduleNewMapper scheduleNewMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private Scheduler scheduler;


    @Scheduled(fixedDelay = 1000*60*10)
    public void sendMessage() {
        System.out.println("开始执行这个定时了=========");
        //获取定时表中的所有数据
        List<ScheduleNewPO> scheduleNewPOList = scheduleNewMapper.getAllSchedulePlan();
        //获取当前运行的计划任务列表
        Set<ScheduleBean> scheduleBeanSet = new HashSet<ScheduleBean>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    System.out.println(jobName+"=="+jobGroup);
                    ScheduleBean scheduleBean = new ScheduleBean();
                    scheduleBean.setJobName(jobName);
                    scheduleBean.setJobGroup(jobGroup);
                    scheduleBeanSet.add(scheduleBean);
                    //List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
//                Date nextFireTime = triggers.get(0).getNextFireTime();
//                System.out.println("[jobName] : " + jobName + " [groupName] : "+ jobGroup + " - " + nextFireTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断两者的差异,打上处理标记
        if (scheduleNewPOList != null && scheduleNewPOList.size() > 0) {
            for (ScheduleNewPO scheduleNewPO : scheduleNewPOList) {
                ScheduleBean scheduleBean = new ScheduleBean();
                scheduleBean.setJobGroup(scheduleNewPO.getAppAgentid());
                scheduleBean.setJobName(scheduleNewPO.getCornExpression());
                if (scheduleBeanSet.contains(scheduleBean)) {
                    //判断是否需要修改
                    if (!"0".equals(scheduleNewPO.getNewOpenFlag())) {
                        scheduleBean.setStatus(ScheduleBean.BEAN_STATUS.UPDATE);
                        //要将标记打成0
                        ScheduleNewPO newPO=new ScheduleNewPO();
                        newPO.setNewOpenFlag("0");
                        Example example = new Example(ScheduleNewPO.class);
                        Example.Criteria criteria = example.createCriteria();
                        criteria.andEqualTo("appAgentid", scheduleNewPO.getAppAgentid());
                        criteria.andEqualTo("cornExpression", scheduleNewPO.getCornExpression());
                        scheduleNewMapper.updateByExampleSelective(newPO,example);
                    } else {
                        scheduleBean.setStatus(ScheduleBean.BEAN_STATUS.KEEP);
                    }
                    scheduleBeanSet.remove(scheduleBean);
                } else {
                    scheduleBean.setStatus(ScheduleBean.BEAN_STATUS.ADD);
                }
                scheduleBeanSet.add(scheduleBean);
            }
        }


        //处理每一个定时bean
        for(ScheduleBean scheduleBean:scheduleBeanSet){
            ScheduleNewPO scheduleNewPO=new ScheduleNewPO();
            List<NewsMsgVO > newsMsgVOList=new ArrayList<NewsMsgVO>();
            getParam(scheduleBean,scheduleNewPO,newsMsgVOList);

            if(scheduleBean.getStatus()==ScheduleBean.BEAN_STATUS.ADD){
                //新增job
                scheduleService.addJob(scheduleBean,scheduleNewPO,newsMsgVOList);
            }else if(scheduleBean.getStatus()==ScheduleBean.BEAN_STATUS.UPDATE){
                //停止 删除 新增
                scheduleService.deleteJob(scheduleBean);
                scheduleService.addJob(scheduleBean,scheduleNewPO,newsMsgVOList);
            }else if(scheduleBean.getStatus()==ScheduleBean.BEAN_STATUS.UNDEFINE||scheduleBean.getStatus()==ScheduleBean.BEAN_STATUS.DELETE){
                // 看怎么设计 认为就是应该删除了
                scheduleService.deleteJob(scheduleBean);

            }
        }



    }
    public void getParam(ScheduleBean scheduleBean,ScheduleNewPO scheduleNewPO, List<NewsMsgVO> newsMsgVOList){

        Example example = new Example(ScheduleNewPO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("markForDelete", 0);
        criteria.andEqualTo("appAgentid", scheduleBean.getJobGroup());
        criteria.andEqualTo("cornExpression", scheduleBean.getJobName());
        example.setOrderByClause("sort asc");


        List<ScheduleNewPO> poList = scheduleNewMapper.selectByExample(example);
        for (int i = 0; i < poList.size(); i++) {
            ScheduleNewPO po = poList.get(i);

            NewsMsgVO newsMsgVO = new NewsMsgVO();
            BeanUtils.copyProperties(po, newsMsgVO);
            newsMsgVO.setDescription(po.getDescription());
            newsMsgVO.setPicurl(po.getPicUrl());
            if (i == 0) {
                scheduleNewPO.setAppSecret(po.getAppSecret());
                scheduleNewPO.setTouser(po.getTouser());
                scheduleNewPO.setToparty(po.getToparty());
                scheduleNewPO.setTotag(po.getTotag());
                scheduleNewPO.setAppAgentid(po.getAppAgentid());
                scheduleNewPO.setCornExpression(po.getCornExpression());


                if(po.getShowDate()==null||"".equals(po.getShowDate())){
                    newsMsgVO.setTitle(po.getTitle());
                }else{
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
                        Calendar c = Calendar.getInstance();
                        c.setTime(new Date());
                        c.add(Calendar.DATE, Integer.parseInt(po.getShowDate()));
                        String dateStr = format.format(c.getTime());
                        newsMsgVO.setTitle(po.getTitle() + "_" + dateStr);
                    }catch (Exception e){
                        e.printStackTrace();
                        newsMsgVO.setTitle(po.getTitle());
                    }

                }

            }
            newsMsgVOList.add(newsMsgVO);
        }


    }


}
