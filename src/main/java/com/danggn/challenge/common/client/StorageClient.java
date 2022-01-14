package com.danggn.challenge.common.client;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface StorageClient {

    void upload(String fileName, InputStream inputStream, ObjectMetadata objectMetadata);

    String getUrl(String fileName);
}
