package xyz.fusheng.model.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName: AliyunOssConfig
 * @Author: code-fusheng
 * @Date: 2020/12/8 23:41
 * @version: 1.0
 * Description: aliyun oss 配置
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private Long policyExpire;

    private Long maxSize;

}
