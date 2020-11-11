package com.springboot.simple.aliyunoss.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云oss对象存储的配置信息
 *
 * @author jgz
 * CreateTime 2020/5/19 9:21
 */
@ConfigurationProperties("aliyun.oss")
@Component
public class AliYunOssProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String domainName;
    private Boolean supportCname = true;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public Boolean getSupportCname() {
        return supportCname;
    }

    public void setSupportCname(Boolean supportCname) {
        this.supportCname = supportCname;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
