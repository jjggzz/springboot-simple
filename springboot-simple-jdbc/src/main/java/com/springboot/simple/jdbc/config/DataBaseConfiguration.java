package com.springboot.simple.jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.springboot.simple.jdbc.datasource.DynamicDataSource;
import com.springboot.simple.jdbc.properties.MasterProperties;
import com.springboot.simple.jdbc.properties.SlaveProperties;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库配置
 * @author JGZ
 * CreateTime 2020/3/29 9:51
 */
@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class DataBaseConfiguration {

    private Logger logger = LoggerFactory.getLogger(DataBaseConfiguration.class);

    @Autowired
    private MasterProperties masterProperties;

    @Autowired
    private SlaveProperties slaveProperties;

    private String mapperLocations;

    private String typeAliasesPackage;

    /**
     * 主库
     * @return
     */
    @Bean(name = "master")
    public DataSource masterDataSource() {
        logger.info("master datasource init start...");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(masterProperties.getDriverClassName());
        druidDataSource.setUrl(masterProperties.getUrl());
        druidDataSource.setUsername(masterProperties.getUsername());
        druidDataSource.setPassword(masterProperties.getPassword());
        //其他配置
        druidDataSource.setInitialSize(masterProperties.getInitialSize());
        druidDataSource.setMinIdle(masterProperties.getMinIdle());
        druidDataSource.setMaxWait(masterProperties.getMaxWaitMillis());
        druidDataSource.setMaxActive(masterProperties.getMaxActive());
        druidDataSource.setTimeBetweenEvictionRunsMillis(masterProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(masterProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(masterProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(masterProperties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(masterProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(masterProperties.getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(masterProperties.getPoolPreparedStatements());
        try {
            druidDataSource.setFilters(masterProperties.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(masterProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setUseGlobalDataSourceStat(masterProperties.getUseGlobalDataSourceStat());
        druidDataSource.setConnectProperties(masterProperties.getConnectionProperties());
        logger.info("master datasource init success...");
        return druidDataSource;
    }

    /**
     * 从库
     * @return
     */
    @Bean(name = "slave")
    public DataSource slaveDataSource() {
        logger.info("slave datasource init start...");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(slaveProperties.getDriverClassName());
        druidDataSource.setUrl(slaveProperties.getUrl());
        druidDataSource.setUsername(slaveProperties.getUsername());
        druidDataSource.setPassword(slaveProperties.getPassword());
        //其他配置
        druidDataSource.setInitialSize(slaveProperties.getInitialSize());
        druidDataSource.setMinIdle(slaveProperties.getMinIdle());
        druidDataSource.setMaxWait(slaveProperties.getMaxWaitMillis());
        druidDataSource.setMaxActive(slaveProperties.getMaxActive());
        druidDataSource.setTimeBetweenEvictionRunsMillis(slaveProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(slaveProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(slaveProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(slaveProperties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(slaveProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(slaveProperties.getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(slaveProperties.getPoolPreparedStatements());
        try {
            druidDataSource.setFilters(slaveProperties.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(slaveProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setUseGlobalDataSourceStat(slaveProperties.getUseGlobalDataSourceStat());
        druidDataSource.setConnectProperties(slaveProperties.getConnectionProperties());
        logger.info("slave datasource init success...");
        return druidDataSource;
    }

    /**
     * 动态数据源
     * @return
     */
    @Bean
    public DynamicDataSource dynamicDataSource(){
        logger.info("dynamic datasource init start...");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource());
        targetDataSources.put("slave", slaveDataSource());
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        logger.info("dynamic datasource init success...");
        return dynamicDataSource;
    }

    /**
     * sql工厂
     * @return
     * @throws IOException
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        logger.info("mybatis sqlfactory init start...");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置动态数据源
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        //设置别名
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        //设置mapper.xml的路径
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(mapperLocations));
        logger.info("mybatis sqlfactory init success...");
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
