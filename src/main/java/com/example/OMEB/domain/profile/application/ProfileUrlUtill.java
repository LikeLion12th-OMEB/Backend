package com.example.OMEB.domain.profile.application;

import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@PropertySource("classpath:/security/application-s3.yml")
public class ProfileUrlUtill {
    @Value("${cloud.profile.s3.default.url}")
    private static String defaultUrl;
    @Value("${cloud.s3.bucket}")
    private static String bucket;
    public static String getDefaultUrl() {
        return defaultUrl;
    }

    public static String createProfilePath(String prefix, String fileName) {
        // 확장자 가져오기
        String extension = getExtension(fileName);

        if (!isImageExtension(extension)) {
            throw new ServiceException(ErrorCode.NOT_FILE_EXTENSION);
        }

        String fileId = UUID.randomUUID().toString();

        return String.format("%s/%s.%s", prefix, fileId, extension);
    }


    private static String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new ServiceException(ErrorCode.NOT_FILE_EXTENSION);
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    private static boolean isImageExtension(String extension) {
        switch (extension) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
                return true;
            default:
                return false;
        }
    }

    public static Map<String, String> parseURL(String url) {
        String[] parts = url.split("https://");
        String domainAndPath = parts[1]; // "bucket.s3.ap-northeast-2.amazonaws.com/prefix/fileId" 형태로 남음

        String[] domainAndRest = domainAndPath.split("/", 2);

        String domain = domainAndRest[0]; // "bucket.s3.ap-northeast-2.amazonaws.com"

        String[] domainParts = domain.split("\\.", 2);
        String bucketName = domainParts[0]; // "bucketName"
        String remainingDomain = domainParts[1]; // "s3.ap-northeast-2.amazonaws.com"

        String path = domainAndRest[1]; // "prefix/fileId"

        String[] pathParts = path.split("/", 2);
        String prefix = pathParts[0]; // "prefix"
        String fileId = pathParts[1]; // "fileId"

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("bucketName", bucketName);
        resultMap.put("domain", remainingDomain);
        resultMap.put("prefix", prefix);
        resultMap.put("fileId", fileId);
        return resultMap;
    }

    public static String validateUrl(String url,String userName) {
        Map<String, String> parsedUrl = parseURL(url);
        String bucketName = parsedUrl.get("bucketName");
        String remainingDomain = parsedUrl.get("domain");
        String prefix = parsedUrl.get("prefix");
        String fileId = parsedUrl.get("fileId");

        if(!remainingDomain.equals("s3.ap-northeast-2.amazonaws.com") || !bucketName.equals(bucket)) {
            throw new ServiceException(ErrorCode.INVALID_S3_URL);
        }
        if(!prefix.equals(userName)) {
            throw new ServiceException(ErrorCode.INVALID_FILE_USER);
        }
        return url;
    }

}
