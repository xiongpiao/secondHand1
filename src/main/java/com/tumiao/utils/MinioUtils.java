package com.tumiao.utils;

import cn.hutool.core.date.DateUtil;
import com.tumiao.Minio.MinioProperties;
import com.tumiao.Minio.UploadResponse;
import io.minio.*;
import io.minio.errors.InsufficientDataException;
import io.minio.messages.Bucket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.client.utils.DateUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Component//将该类实例化成一个bean,并由spring管理
@Slf4j//开启日志
//上传文件的工具类
public class MinioUtils {
    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private MinioClient minioClient;

    private final Long maxSize = (long) (1024 * 1024);

    /**
     * 创建bucket
     */
    public void createBucket(String bucketName) throws Exception
    {
        if(!minioClient.bucketExists(
                BucketExistsArgs.builder()
                .bucket(bucketName).build() ))//判断桶是否已存在
        {
            minioClient.makeBucket(MakeBucketArgs.builder()
            .bucket(bucketName).build());//如果桶不存在则创建
        }
    }
    /**
     * 上传文件
     */
    public UploadResponse uploadFile(MultipartFile file,String bucketName) throws  Exception
    {
        //判断文件是否为空
        if (null == file || 0 == file.getSize()) {
            return null;
        }
        //判断存储桶是否存在  不存在则创建
        createBucket(bucketName);
        //文件名
        String originalFilename = file.getOriginalFilename();
        //新的文件名 = 时间戳_随机数.后缀名
        assert originalFilename != null;
        long now = System.currentTimeMillis() / 1000;
        String fileName = DateUtil.format(DateUtil.date(),"yyyyMMdd")+"_"+ now + "_" + new Random().nextInt(1000) +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        //开始上传
        log.info("file压缩前大小:{}",file.getSize());
        //如果大于最大上传大小则进行压缩
        if (file.getSize() > maxSize) {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            FileItem fileItem = fileItemFactory.createItem(fileName, "text/plain", true, fileName);
            OutputStream outputStream = fileItem.getOutputStream();
            Thumbnails.of(file.getInputStream()).scale(1f).outputFormat(originalFilename.substring(originalFilename.lastIndexOf(".")+1)).outputQuality(0.25f).toOutputStream(outputStream);
            file = new CommonsMultipartFile((org.apache.commons.fileupload.FileItem) fileItem);
        }
        log.info("file压缩后大小:{}",file.getSize());
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                        file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
        String url = minioProperties.getEndpoint() + "/" + bucketName + "/" + fileName;
//        String urlHost = minioProperties.getNginxHost() + "/" + bucketName + "/" + fileName;
        return new UploadResponse(url);
    }
    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() throws Exception {
        return minioClient.listBuckets();
    }
    /**
     * 根据bucketName获取信息
     */
    public Optional<Bucket> getBucket(String bucketName) throws Exception {
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }
    /**
     * 根据bucketName删除信息
     */
    public void removeBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }
    /**
     * 获取⽂件外链
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    public String getObjectURL(String bucketName, String objectName, Integer expires) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(expires).build());
    }
    /**
     * 获取⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @return ⼆进制流
     */
    public InputStream getObject(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }
    /**
     * 上传⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param stream     ⽂件流
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws
            Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, stream.available(), -1).contentType(objectName.substring(objectName.lastIndexOf("."))).build());
    }
    /**
     * 上传⽂件
     *
     * @param bucketName  bucket名称
     * @param objectName  ⽂件名称
     * @param stream      ⽂件流
     * @param size        ⼤⼩
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, long
            size, String contextType) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1).contentType(contextType).build());
    }

    /**
     * 获取⽂件信息
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#statObject
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 删除⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @throws Exception https://docs.minio.io/cn/java-minioClient-apireference.html#removeObject
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }


    /***
     * 上传视频
     * @param file
     * @param bucketName
     * @return
     * @throws Exception
     */
    public UploadResponse uploadVideo(MultipartFile file, String bucketName) throws Exception {
        //判断文件是否为空
        if (null == file || 0 == file.getSize()) {
            return null;
        }
        //判断存储桶是否存在  不存在则创建
        createBucket(bucketName);
        //文件名
        String originalFilename = file.getOriginalFilename();
        //新的文件名 = 时间戳_随机数.后缀名
        assert originalFilename != null;
        long now = System.currentTimeMillis() / 1000;
        String fileName = DateUtil.format(DateUtil.date(),"yyyyMMdd")+"_"+ now + "_" + new Random().nextInt(1000) +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        //开始上传
        log.info("file大小:{}",file.getSize());
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                        file.getInputStream(), file.getSize(), -1)
                        .contentType("video/mp4")
                        .build());
        String url = minioProperties.getEndpoint() + "/" + bucketName + "/" + fileName;
//        String urlHost = minioProperties.getNginxHost() + "/" + bucketName + "/" + fileName;
        return new UploadResponse(url);
    }
}
