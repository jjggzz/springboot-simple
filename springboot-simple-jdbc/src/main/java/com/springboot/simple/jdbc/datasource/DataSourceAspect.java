package com.springboot.simple.jdbc.datasource;

import com.springboot.simple.jdbc.annotation.DataSource;
import com.springboot.simple.jdbc.properties.JdbcProperties;
import com.springboot.simple.support.util.DateUtils;
import com.springboot.simple.support.util.RandomUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * 动态切换aop
 * @author JGZ
 * CreateTime 2020/3/29 11:07
 */
@Order(value = 2)
@Aspect
@Component
public class DataSourceAspect {

    @Autowired
    private JdbcProperties jdbcProperties;

    @Pointcut("@annotation(com.springboot.simple.jdbc.annotation.DataSource)")
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void before(JoinPoint point) {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    @After("annotationPointcut()")
    public void after() {
        // 需要验证
        DynamicDataSourceHolder.clearDataSource();
    }

    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                if (DynamicDataSource.SLAVE.equals(source.value())){
                    if (CollectionUtils.isNotEmpty(jdbcProperties.getSlaveList())) {
                        Random random = new Random(DateUtils.currentTimeInMillis());
                        DynamicDataSourceHolder.setDataSource(DynamicDataSource.SLAVE + (random.nextInt() % (jdbcProperties.getSlaveList().size())));
                    } else {
                        DynamicDataSourceHolder.setDataSource(DynamicDataSource.MASTER);
                    }
                } else {
                    DynamicDataSourceHolder.setDataSource(DynamicDataSource.MASTER);
                }
            }
            // 方法注解可以覆盖类型注解
            if (method.isAnnotationPresent(DataSource.class)) {
                DataSource source = method.getAnnotation(DataSource.class);
                if (DynamicDataSource.SLAVE.equals(source.value())){
                    if (CollectionUtils.isNotEmpty(jdbcProperties.getSlaveList())) {
                        Random random = new Random(DateUtils.currentTimeInMillis());
                        DynamicDataSourceHolder.setDataSource(DynamicDataSource.SLAVE + (random.nextInt() % (jdbcProperties.getSlaveList().size())));
                    } else {
                        DynamicDataSourceHolder.setDataSource(DynamicDataSource.MASTER);
                    }
                } else {
                    DynamicDataSourceHolder.setDataSource(DynamicDataSource.MASTER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
