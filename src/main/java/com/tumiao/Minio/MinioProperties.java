package com.tumiao.Minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//Minio配置文件的配置类
@ConfigurationProperties(prefix = "minio")//对应配置文件，将配置文件的数据注入，解耦合
@Component//将该类实例化成一个bean,并由spring管理
@Data//lombok
public class MinioProperties {
    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;

}
