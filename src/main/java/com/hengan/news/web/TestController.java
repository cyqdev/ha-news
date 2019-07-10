package com.hengan.news.web;

import com.hengan.news.common.util.QYWXUtil;
import com.hengan.news.common.util.WorkWXAPI;
import com.hengan.news.core.Constant;
import com.hengan.news.mapper.ScheduleMapper;
import com.hengan.news.model.po.SchedulePO;
import com.hengan.news.model.vo.NewsMsgVO;
import com.hengan.news.model.vo.NewsVO;
import com.hengan.news.schedule.SendNewsMsgSchedule;
import com.hengan.news.service.NewsService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author cyq
 * @version 1.0
 * @date 2019/7/10 11:02
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private SendNewsMsgSchedule sendNewsMsgSchedule;

    @ApiOperation(value="测试手动发新闻", notes="测试手动发新闻")
    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public String detail() {
//        SendOperationInfo();
        sendNewsMsgSchedule.sendMessage();
//        sendNewsMsgSchedule.SendOperationInfo(Constant.APP_CODE.SJZX);
        return "你真棒";
    }


}
