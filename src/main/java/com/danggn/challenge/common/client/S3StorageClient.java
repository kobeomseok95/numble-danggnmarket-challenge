package com.danggn.challenge.common.client;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class S3StorageClient implements StorageClient {

    private final AmazonS3Client amazonS3Client;
    private final S3Component s3Component;

    @Override
    public void upload(String fileName, InputStream inputStream, ObjectMetadata objectMetadata) {
        amazonS3Client.putObject(
                new PutObjectRequest(
                        getFilePath(),
                        fileName,
                        inputStream,
                        objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
    }

    private String getFilePath() {
        return s3Component.getBucket() + FilePath.IMAGE.getPath();
    }

    @Override
    public String getUrl(String fileName) {
        return String.valueOf(amazonS3Client.getUrl(getFilePath(), fileName));
    }
}
