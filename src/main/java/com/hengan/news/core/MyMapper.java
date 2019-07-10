package com.hengan.news.core;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.*;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 */

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>{
}
