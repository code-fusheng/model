/**
 * @FileName: UploadService
 * @Author: code-fusheng
 * @Date: 2020/5/4 20:19
 * Description: 文件服务工具类
 */
package xyz.fusheng.model.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xyz.fusheng.code.springboot.core.plugin.oss.AliyunOssConfig;
import xyz.fusheng.model.common.config.UploadConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@EnableConfigurationProperties(UploadConfig.class)
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private AliyunOssConfig aliyunOssConfig;

    public UploadService() {
    }

    public String uploadImage(MultipartFile file) {
        // 1.校验文件类型
        String contentType = file.getContentType();
        if (!uploadConfig.getAllowTypes().contains(contentType)) {
            throw new RuntimeException("文件类型不支持");
        }
        try {
            // 3、上传到FastDFS
            // 3.1、获取扩展名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            // 3.2、上传
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            // 返回路径
            return uploadConfig.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            logger.error("【文件上传】上传文件失败！....{}", e.getMessage(), e);
            throw new RuntimeException("【文件上传】上传文件失败！" + e.getMessage());
        }
    }

    public String uploadImageToAliyunOss(MultipartFile file) throws IOException {
        String endpoint = aliyunOssConfig.getEndpoint();
        String accessKeyId = aliyunOssConfig.getAccessKeyId();
        String accessKeySecret = aliyunOssConfig.getAccessKeySecret();
        String bucketName = aliyunOssConfig.getBucketName();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, file.getOriginalFilename(), new ByteArrayInputStream(file.getBytes()));
        ossClient.shutdown();
        String url = "https://" + bucketName + "." + endpoint + "/" + file.getOriginalFilename();
        return url;
    }

    public String uploadFileToAliyunOss(MultipartFile file) {
        OSS ossClient = new OSSClientBuilder().build(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessKeyId(), aliyunOssConfig.getAccessKeySecret());
        try {
            ossClient.putObject(aliyunOssConfig.getBucketName(), file.getOriginalFilename(), new ByteArrayInputStream(file.getBytes()));
            // 判断容器是否存在,不存在就创建
            if (!ossClient.doesBucketExist(aliyunOssConfig.getBucketName())) {
                ossClient.createBucket(aliyunOssConfig.getBucketName());
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(aliyunOssConfig.getBucketName());
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        String url = "https://" + aliyunOssConfig.getBucketName() + "." + aliyunOssConfig.getEndpoint() + "/" + file.getOriginalFilename();
        logger.info("文件上传返回结果预览:{}", url);
        return url;
    }

}
