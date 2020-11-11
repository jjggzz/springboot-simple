package com.springboot.simple.aliyunoss.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.springboot.simple.aliyunoss.properties.AliYunOssProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 阿里云oss工具
 *
 * @author jgz
 * CreateTime 2020/5/19 14:16
 */
@Component
public class AliYunOssUtils {

    @Autowired
    private OSS ossClient;

    @Autowired
    private AliYunOssProperties aliYunOssProperties;

    /**
     * 本地上传文件实现，会随机生成名字
     * TODO
     * @param localPath 本地文件路径
     * @param bucketName oss的bucketName
     * @param ossPath oss上的路径
     * @return {@link java.lang.String 返回文件的外网访问路径}
     * @author jgz
     * @date 2020/5/19 15:20
     */
    public String uploadLocalFileToOss(String localPath, String bucketName, String ossPath){

        return null;
    }

    /**
     * 本地上传文件实现，可以指定文件名
     * @param localPath 本地文件路径
     * @param bucketName oss的bucketName
     * @param ossPath oss上的路径
     * @param fileName 文件名
     * @return {@link java.lang.String 返回文件的外网访问路径}
     * @author jgz
     * @date 2020/5/19 15:20
     */
    public String uploadLocalFileToOss(String localPath, String bucketName, String ossPath, String fileName){
        //创建本地文件类
        File file = new File(localPath);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,ossPath + fileName,file);
        ObjectMetadata metadata = new ObjectMetadata();
        //设置mime类型
        metadata.setContentType(this.getContentType(localPath));
        putObjectRequest.setMetadata(metadata);
        ossClient.putObject(putObjectRequest);

        //如果绑定了自己的域名则返回带自己域名的路径
        if(aliYunOssProperties.getSupportCname() && StringUtils.isNotBlank(aliYunOssProperties.getDomainName())){
            return aliYunOssProperties.getDomainName() + "/" + ossPath + fileName;
        }
        return bucketName + "." + aliYunOssProperties.getEndpoint() + "/" + ossPath + fileName;
    }


    /**
     * 获取硬盘中文件的mime类型
     * @param filename 文件名
     * @return {@link String 文件的mime类型}
     * @author jgz
     * @date 2020/5/19 15:07
     */
    private String getContentType(String filename){
        String type = null;
        Path path = Paths.get(filename);
        try {
            type = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return type;
    }


}
