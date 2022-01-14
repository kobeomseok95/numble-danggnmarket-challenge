package com.danggn.challenge.common.manager.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class MultipartFilesEmptyChecker {

    public static List<MultipartFile> checkIfEmptyReturnEmptyCollection(List<MultipartFile> sourceFiles) {
        if (sourceFiles.size() == 1 && Objects.equals(sourceFiles.get(0).getOriginalFilename(), "")) {
            return Collections.emptyList();
        }
        return sourceFiles;
    }
}
