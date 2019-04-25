package com.hengan.news.configurer;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.hengan.news.datasource.DatabaseType;
import com.hengan.news.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.hengan.news.core.ProjectConstant.MODEL_PACKAGE;

/**
 * Mybatis & MyMapper & PageHelper 配置
 */
@Configuration
//@MapperScan(basePackages = "com.hengan.news.dao")
public class MybatisConfigurer {

    @Autowired
    private Environment env;

    @Value("${datasource.news.url}")
    private String url;


    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     */
    @Bean
    @ConfigurationProperties(prefix = "datasource.news")
    public DataSource newsDbDataSource() throws Exception {

        //方式一：
        //DataSource build = DataSourceBuilder.create()
        //.driverClassName(env.getProperty("datasource.news.driver-class-name"))
        //.url(env.getProperty("datasource.news.url"))
        //.username(env.getProperty("datasource.news.username"))
        //.password(env.getProperty("datasource.news.password")).build();
        //return build;

        /**
         *  方式二：
         *      DataSourceBuilder.create().build();
         */

        //方式三：

        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("datasource.news.driver-class-name"));
        props.put("url", env.getProperty("datasource.news.url"));
        props.put("username", env.getProperty("datasource.news.username"));
        props.put("password", env.getProperty("datasource.news.password"));
        return DruidDataSourceFactory.createDataSource(props);

    }

    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix = "datasource.user")
    public DataSource userDataSource() {
        DataSource build = DataSourceBuilder.create()
                .driverClassName(env.getProperty("datasource.user.driver-class-name"))
                .url(env.getProperty("datasource.user.url"))
                .username(env.getProperty("datasource.user.username"))
                .password(env.getProperty("datasource.user.password")).build();
        return build;

//        return DataSourceBuilder.create().build();
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("newsDbDataSource") DataSource newsdb,@Qualifier("userDataSource") DataSource userdb) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.newsdb, newsdb);
        targetDataSources.put(DatabaseType.userdb, userdb);

        DynamicDataSource dataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 默认的datasource设置为mydb
        dataSource.setDefaultTargetDataSource(newsdb);
        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DynamicDataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setTypeAliasesPackage(MODEL_PACKAGE);
        // 指定数据源(这个必须有，否则报错)
        fb.setDataSource(ds);
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
//        fb.setTypeAliasesPackage("com.hengan.news.dao.*");// 指定基包
        //指定xml位置
//        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));
        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }



//    @Bean
//    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setTypeAliasesPackage(MODEL_PACKAGE);
//
//        //配置分页插件，详情请查阅官方文档
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("pageSizeZero", "true");//分页尺寸为0时查询所有纪录不再执行分页
//        properties.setProperty("reasonable", "true");//页码<=0 查询第一页，页码>=总页数查询最后一页
//        properties.setProperty("supportMethodsArguments", "true");//支持通过 MyMapper 接口参数来传递分页参数
//        pageHelper.setProperties(properties);
//
//        //添加插件
//        factory.setPlugins(new Interceptor[]{pageHelper});
//
//        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        factory.setMapperLocations(resolver.getResources("classpath:myMapper/*.xml"));
//        return factory.getObject();
//    }

//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//        mapperScannerConfigurer.setBasePackage(MAPPER_PACKAGE);
//        //配置通用Mapper，详情请查阅官方文档
//        Properties properties = new Properties();
//        properties.setProperty("mappers", MAPPER_INTERFACE_REFERENCE);
//        properties.setProperty("notEmpty", "false");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
//        properties.setProperty("IDENTITY", "MYSQL");
//        mapperScannerConfigurer.setProperties(properties);
//
//        return mapperScannerConfigurer;
//    }

}

