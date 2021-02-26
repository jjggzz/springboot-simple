package com.springboot.simple.jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.springboot.simple.jdbc.datasource.DynamicDataSource;
import com.springboot.simple.jdbc.properties.JdbcProperties;
import com.springboot.simple.jdbc.properties.SlaveProperties;
import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库配置
 * @author JGZ
 * CreateTime 2020/3/29 9:51
 */
@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class DataBaseConfiguration {

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcProperties jdbcProperties;

    private String mapperLocations;

    private String typeAliasesPackage;

    /**
     * 主库
     * @return
     */
    @Bean(name = "master")
    public DataSource masterDataSource() {
        LOGGER.debug("master datasource init start...");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(jdbcProperties.getMaster().getDriverClassName());
        druidDataSource.setUrl(jdbcProperties.getMaster().getUrl());
        druidDataSource.setUsername(jdbcProperties.getMaster().getUsername());
        druidDataSource.setPassword(jdbcProperties.getMaster().getPassword());
        //其他配置
        druidDataSource.setInitialSize(jdbcProperties.getMaster().getInitialSize());
        druidDataSource.setMinIdle(jdbcProperties.getMaster().getMinIdle());
        druidDataSource.setMaxWait(jdbcProperties.getMaster().getMaxWaitMillis());
        druidDataSource.setMaxActive(jdbcProperties.getMaster().getMaxActive());
        druidDataSource.setTimeBetweenEvictionRunsMillis(jdbcProperties.getMaster().getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(jdbcProperties.getMaster().getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(jdbcProperties.getMaster().getValidationQuery());
        druidDataSource.setTestWhileIdle(jdbcProperties.getMaster().getTestWhileIdle());
        druidDataSource.setTestOnBorrow(jdbcProperties.getMaster().getTestOnBorrow());
        druidDataSource.setTestOnReturn(jdbcProperties.getMaster().getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(jdbcProperties.getMaster().getPoolPreparedStatements());
        try {
            druidDataSource.setFilters(jdbcProperties.getMaster().getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(jdbcProperties.getMaster().getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setUseGlobalDataSourceStat(jdbcProperties.getMaster().getUseGlobalDataSourceStat());
        druidDataSource.setConnectProperties(jdbcProperties.getMaster().getConnectionProperties());
        LOGGER.debug("master datasource init success...");
        return druidDataSource;
    }

    /**
     * 从库
     * @return
     */
    @Bean(name = "slaveList")
    public List<DataSource> slaveDataSource() {
        LOGGER.debug("slave datasource init start...");
        List<DataSource> dataSourceList = new ArrayList<>();
        if (CollectionUtils.isEmpty(jdbcProperties.getSlaveList())) {
            return dataSourceList;
        }
        for (SlaveProperties properties : jdbcProperties.getSlaveList()) {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(properties.getDriverClassName());
            druidDataSource.setUrl(properties.getUrl());
            druidDataSource.setUsername(properties.getUsername());
            druidDataSource.setPassword(properties.getPassword());
            //其他配置
            druidDataSource.setInitialSize(properties.getInitialSize());
            druidDataSource.setMinIdle(properties.getMinIdle());
            druidDataSource.setMaxWait(properties.getMaxWaitMillis());
            druidDataSource.setMaxActive(properties.getMaxActive());
            druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
            druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
            druidDataSource.setValidationQuery(properties.getValidationQuery());
            druidDataSource.setTestWhileIdle(properties.getTestWhileIdle());
            druidDataSource.setTestOnBorrow(properties.getTestOnBorrow());
            druidDataSource.setTestOnReturn(properties.getTestOnReturn());
            druidDataSource.setPoolPreparedStatements(properties.getPoolPreparedStatements());
            try {
                druidDataSource.setFilters(properties.getFilters());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
            druidDataSource.setUseGlobalDataSourceStat(properties.getUseGlobalDataSourceStat());
            druidDataSource.setConnectProperties(properties.getConnectionProperties());
            dataSourceList.add(druidDataSource);
        }
        LOGGER.debug("slave datasource init success...");
        return dataSourceList;
    }

    /**
     * 动态数据源
     * @return
     */
    @Bean
    public DynamicDataSource dynamicDataSource(){
        LOGGER.debug("dynamic datasource init start...");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource());
        for (int i = 0; i < slaveDataSource().size(); i++) {
            targetDataSources.put("slave" + i, slaveDataSource().get(i));
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        LOGGER.debug("dynamic datasource init success...");
        return dynamicDataSource;
    }

    /**
     * sql工厂
     * @return
     * @throws IOException
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        LOGGER.debug("mybatis sqlFactory init start...");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置动态数据源
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        //设置别名
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        //设置mapper.xml的路径
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(mapperLocations));
        LOGGER.debug("mybatis sqlFactory init success...");
        return sqlSessionFactoryBean;
    }

    /**
     * 事务控制
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        //设置动态数据源事务
        transactionManager.setDataSource(dynamicDataSource());
        return transactionManager;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }
}
