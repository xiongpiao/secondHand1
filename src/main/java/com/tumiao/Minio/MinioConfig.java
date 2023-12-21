package com.tumiao.Minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建Minio客户端
 */
@Configuration//表示该类是spring表示的对象
@EnableConfigurationProperties(MinioProperties.class)//启用指定类MinioProperties的配置属性，使可以使用@Autowired进行注入
public class MinioConfig {
    @Autowired
    private MinioProperties minioProperties;//以配置类来进行，解耦合
    @Bean//被@Bean注解的方法,返回值将被作为spring管理的bean
    public MinioClient minioClient()
    {
        return MinioClient.builder()
               .endpoint(minioProperties.getEndpoint())
               .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
               .build();
    }
}
