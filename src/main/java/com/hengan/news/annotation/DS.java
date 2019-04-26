package com.hengan.news.annotation;

import com.hengan.news.config.datasource.DatabaseType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 此注解用来切换数据源
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DS {
    //此处接收的是数据源的名称
    DatabaseType value();
}
