package com.forfries.ideaifile.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OSSClientUtil {
    // 你的Bucket Name
    @Getter
    private static final String BUCKET_NAME = "ideai-image";
    // 你的Endpoint, 比如华东1: http://oss-cn-hangzhou.aliyuncs.com
    @Getter
    private static final String ENDPOINT = "oss-cn-nanjing.aliyuncs.com";
//    // 你的AccessKeyId
    private static final String ACCESS_KEY_ID = "LTAI5tRDQ78nM5K3KdvLEBfR";
//    // 你的AccessKeySecret
    private static final String ACCESS_KEY_SECRET = "B6wo0YF0em7tlp6D8RflnfoVOJ3SV8";


    private static final String region = "cn-nanjing";
    // 创建 OSSClient 实例
    public static OSS createOSSClient() throws com.aliyuncs.exceptions.ClientException {
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET);


//        EnvironmentVariableCredentialsProvider credentialsProvider = null;
        try {

            return  OSSClientBuilder.create()
                    .endpoint(ENDPOINT)
                    .credentialsProvider(credentialsProvider)
                    .clientConfiguration(clientBuilderConfiguration)
                    .region(region)
                    .build();

        } catch (ClientException e) {
            log.error("无法获取OSS密钥：{}",e.getMessage());
            throw new RuntimeException(e);
        }

    }


    // 关闭 OSSClient
    public static void closeOSSClient(OSS ossClient) {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}
