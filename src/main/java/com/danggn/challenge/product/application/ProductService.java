package com.danggn.challenge.product.application;

import com.danggn.challenge.common.client.StorageClient;
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

//    private final FileManager fileManager;
    private final StorageClient storageClient;
    private final ProductJpaRepository productJpaRepository;
    private final ProductApplicationAssembler applicationAssembler;

    // TODO fileManager 구현
    // TODO VO 스펙 변경될 것 같다. S3 클라이언트 인터페이스 스펙 보고 결정하기
    @Override
    public Long save(
            CreateProductRequestVo createProductRequestVo,
            Long memberId
    ) {
        List<String> urls = createProductRequestVo.getFileNames().stream()
                .map(storageClient::getUrl)
                .collect(Collectors.toList());
        Product product = applicationAssembler.toProductEntity(createProductRequestVo, memberId, urls);
        return productJpaRepository.save(product).getId();
    }
}
