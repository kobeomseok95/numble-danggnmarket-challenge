package com.danggn.challenge.common.manager.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileManager {

    List<String> upload(List<MultipartFile> files);
}
