package com.hengan.news.web;

import com.github.pagehelper.PageInfo;
import com.hengan.news.core.Result;
import com.hengan.news.core.ResultGenerator;
import com.hengan.news.dao.NewsMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.vo.NewsVO;
import com.hengan.news.service.NewsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by cyq on 2019/02/12.
*/
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsMapper newsMapper;


    @ApiOperation(value="获取新闻详情", notes="获取新闻详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(name="id") Long id) {
        NewsVO news = newsService.detail(id);
        return ResultGenerator.genSuccessResult(news);
    }

    @ApiOperation(value="获取新闻列表信息", notes="获取新闻列表信息")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(@RequestBody(required = false) NewsVO newsVO) {
//        PageHelper.startPage(1, 10);
        List<NewsPO> news = newsMapper.selectPage1(newsVO);
        PageInfo pageInfo = new PageInfo(news);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
