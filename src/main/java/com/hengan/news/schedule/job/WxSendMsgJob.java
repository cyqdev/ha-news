package com.hengan.news.schedule.job;

import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import com.hengan.news.model.vo.NewsMsgVO;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        //取出消息内容
        List<NewsMsgVO> newsMsgVOList=(List<NewsMsgVO>) jobDataMap.get("mpnewsVOS");

        //需要处理日期
        String title=jobDataMap.getString("title");
        String showDate=jobDataMap.getString("showDate");
        if(showDate!=null&&!"".equals(showDate)){
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, Integer.parseInt(showDate));
                String dateStr = format.format(c.getTime());
                newsMsgVOList.get(0).setTitle(title+ "_" + dateStr);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        QYWXUtil.sendnNewsMessage(accessToken, jobDataMap.getString("touser"), jobDataMap.getString("toparty"), jobDataMap.getString("totag"), jobDataMap.getInt("agentid"), newsMsgVOList);

    }
}
