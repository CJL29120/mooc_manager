package com.chen.mooc_manager.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myconfig.aliyun")
@PropertySource(value = "classpath:/ossconfig.yml",factory = YamlPropertySourceFactory.class)
public class OssUploadParam {
    private String accessId;
    private String accessKey;
    private String bucket;
    private String endpoint;
    private String callbackUrl;

    private OSS ossClient;

    public OSS getOssClient(){
        if (ossClient==null){
            ossClient= new OSSClientBuilder().build(endpoint, accessId, accessKey,new ClientBuilderConfiguration());
        }
        return ossClient;
    }

    public String getOssUrlPrefix(){
        return "https://"+bucket+"."+endpoint;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void setOssClient(OSS ossClient) {
        this.ossClient = ossClient;
    }
}
