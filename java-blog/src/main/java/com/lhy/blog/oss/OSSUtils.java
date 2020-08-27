package com.lhy.blog.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

public class OSSUtils {

    private static final String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private static final String accessKeyId = "";
    private static final String accessKeySecret = "";
    private static final String bucketName = "blog-lhy";

    public static String getUrl(String objectName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置URL过期时间为1分钟。
        Date expiration = new Date(new Date().getTime() + 60 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);

        // 关闭OSSClient。
        ossClient.shutdown();
        return url.toString();
    }

    public static void upload(MultipartFile file, String objectName) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
        ossClient.putObject(putObjectRequest);
        // 设置权限为公共读
        ossClient.setObjectAcl(bucketName, objectName, CannedAccessControlList.PublicRead);
        ossClient.shutdown();
    }

    public static void uploadBase64(String objectName, String base64){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        byte[] imageByte = Base64.getMimeDecoder().decode(base64);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(imageByte));
        ossClient.putObject(putObjectRequest);
        // 设置权限为公共读
        ossClient.setObjectAcl(bucketName, objectName, CannedAccessControlList.PublicRead);
        ossClient.shutdown();
    }

}