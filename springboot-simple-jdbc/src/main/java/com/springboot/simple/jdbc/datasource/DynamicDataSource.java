package com.springboot.simple.jdbc.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author JGZ
 * CreateTime 2020/3/29 11:05
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 数据源-主库
     */
    public static final String MASTER = "master";
    /**
     * 数据源-从库
     */
    public static final String SLAVE = "slave";

    @Override
    protected Object determineCurrentLookupKey() {
        // 从自定义的位置获取数据源标识
        return DynamicDataSourceHolder.getDataSource();
    }
}