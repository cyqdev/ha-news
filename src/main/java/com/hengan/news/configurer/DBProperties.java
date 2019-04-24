package com.hengan.news.configurer;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Cyq
 * @Date 2019/4/24 17:14
 **/
//@Component
//@ConfigurationProperties(prefix = "datasource")
public class DBProperties {
    private HikariDataSource news;
    private HikariDataSource user;

    public HikariDataSource getNews() {
        return news;
    }

    public void setNews(HikariDataSource news) {
        this.news = news;
    }

    public HikariDataSource getUser() {
        return user;
    }

    public void setUser(HikariDataSource user) {
        this.user = user;
    }
}
