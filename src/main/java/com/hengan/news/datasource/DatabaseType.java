package com.hengan.news.datasource;

/**
 * 列出所有的数据源key（常用数据库名称来命名）
 * 注意：
 * 1）这里数据源与数据库是一对一的
 * 2）DatabaseType中的变量名称就是数据库的名称
 */
public enum DatabaseType {
    /**
     * 新闻数据源
     */
    newsdb,
    /**
     * 用户数据源
     */
    userdb
}
