package com.springboot.simple.aliyunoss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.springboot.simple.aliyunoss.properties.AliYunOssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云oss配置
 *
 * @author jgz
 * CreateTime 2020/5/19 13:54
 */
@Configuration
public class AliYunOssConfiguration {

    private Logger logger = LoggerFactory.getLogger(AliYunOssConfiguration.class);

    @Autowired
    private AliYunOssProperties aliYunOssProperties;

    /**
     * 初始化阿里OSS客户端
     * @param
     * @return {@link OSSClient}
     * @author jgz
     * @date 2020/5/19 13:57
     */
    @Bean
    public OSS ossClient(){
        logger.info("aliyun OSSClient init start...");
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();

        conf.setSupportCname(aliYunOssProperties.getSupportCname());

        OSS ossClient = new OSSClientBuilder()
                .build(aliYunOssProperties.getEndpoint(),
                        aliYunOssProperties.getAccessKeyId(),
                        aliYunOssProperties.getAccessKeySecret(),
                        conf);
        logger.info("aliyun OSSClient init success...");
        return ossClient;
    }


}
