package com.danggn.challenge.product.application;

import com.danggn.challenge.common.client.StorageClient;
import com.danggn.challenge.common.manager.file.FileManager;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.domain.Product;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
class ProductService implements ProductUseCase {

    private final FileManager fileManager;
    private final StorageClient storageClient;
    private final ProductJpaRepository productJpaRepository;
    private final ProductApplicationAssembler applicationAssembler;

    @Override
    public Long save(
            CreateProductRequestVo createProductRequestVo,
            LoginMember loginMember
    ) {
        List<String> uploadedFileNames = fileManager.upload(createProductRequestVo.getFiles());
        List<String> urls = uploadedFileNames.stream()
                .map(storageClient::getUrl)
                .collect(Collectors.toList());

        Product product = productJpaRepository.save(
                applicationAssembler.toProductEntity(createProductRequestVo, loginMember.getMember())
        );
        product.addProductImages(
                applicationAssembler.toProductImageEntity(product, urls)
        );
        return product.getId();
    }
}
