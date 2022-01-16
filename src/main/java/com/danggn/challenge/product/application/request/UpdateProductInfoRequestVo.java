package com.danggn.challenge.product.application.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Value
@EqualsAndHashCode(of = {"productId"})
@Builder
public class UpdateProductInfoRequestVo {

    private Long productId;

    private final List<MultipartFile> files;

    private final String name;

    private final String category;

    private final Long price;

    private final String mainText;

    public List<String> getFileNames() {
        return files.stream()
                .map(MultipartFile::getOriginalFilename)
                .collect(Collectors.toList());
    }
}
