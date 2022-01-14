package com.danggn.challenge.common.manager;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.danggn.challenge.common.client.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DefaultFileManager implements FileManager {

    private final StorageClient storageClient;

    @Override
    public List<String> upload(List<MultipartFile> files) {
        return getUploadedRenameFileNames(files);
    }

    private List<String> getUploadedRenameFileNames(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = getRenameFileName(file);
            fileNames.add(filename);
            uploadToFileStorage(file, filename);
        }
        return fileNames;
    }

    private String getRenameFileName(MultipartFile file) {
        return UUID.randomUUID()
                .toString()
                .concat(getFileExtension(
                        Objects.requireNonNull(file.getOriginalFilename())));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다.", fileName));
        }
    }

    private void uploadToFileStorage(MultipartFile file, String filename) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()){
            storageClient.upload(filename, inputStream, objectMetadata);
        } catch(IOException e) {
            throw new IllegalArgumentException(String.format("파일 업로드 중 에러가 발생했습니다. (%s)", file.getOriginalFilename()));
        }
    }
}
