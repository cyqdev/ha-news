package com.hengan.news.mapper;

import com.hengan.news.core.MyMapper;
import com.hengan.news.model.po.NewsPO;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.NewsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper extends MyMapper<UserPO> {

}