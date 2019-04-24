package com.hengan.news.configurer;

import com.hengan.news.annotation.DS;
import com.hengan.news.datasource.DatabaseContextHolder;
import com.hengan.news.datasource.DatabaseType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author Cyq
 * @Date 2019/4/24 17:25
 **/
@Component
@Aspect
public class DataSourceAspect {
//    //切换放在mapper接口的方法上，所以这里要配置AOP切面的切入点
//    @Pointcut("execution( * com.hengan.news.dao.*.*(..))")
//    public void dataSourcePointCut() {
//    }
//
//    @Before("dataSourcePointCut()")
//    public void before(JoinPoint joinPoint) {
//        Object target = joinPoint.getTarget();
//        String method = joinPoint.getSignature().getName();
//        Class<?>[] clazz = target.getClass().getInterfaces();
//        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
//        try {
//            Method m = clazz[0].getMethod(method, parameterTypes);
//            //如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
//            if (m != null && m.isAnnotationPresent(DS.class)) {
//                DS data = m.getAnnotation(DS.class);
//                DatabaseType dataSourceName = data.value();
//                DatabaseContextHolder.setDatabaseType(dataSourceName);
//                System.out.println("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
//            } else {
//                System.out.println("switch datasource fail,use default");
//            }
//        } catch (Exception e) {
//            System.out.println("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error"+e.getMessage());
//        }
//    }
//
//    //执行完切面后，将线程共享中的数据源名称清空
//    @After("dataSourcePointCut()")
//    public void after(JoinPoint joinPoint){
//        DatabaseContextHolder.clearDatabaseType();
//    }
}
